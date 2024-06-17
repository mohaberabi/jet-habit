package com.mohaberabi.jethabbit.features.habit.presentation.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.util.error.asUiText
import com.mohaberabi.jethabbit.features.habit.domain.usecase.HabitUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: HabitUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    companion object {
        const val DATE_KEY = "choosedDateKey"
    }

    val choosedDate: StateFlow<ZonedDateTime> =
        savedStateHandle.getStateFlow(DATE_KEY, ZonedDateTime.now())

    private val _event = Channel<HomeEvents>()
    val event = _event.receiveAsFlow()

    val state = choosedDate
        .flatMapLatest { date ->
            useCases.getHabitsByDayOfWeekUseCase(date.dayOfWeek.name)
        }.map { HomeState.Done(it) as HomeState }
        .onStart { emit(HomeState.Loading) }
        .catch {
            it.printStackTrace()
            emit(HomeState.Error)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            HomeState.Loading
        )

    init {
        syncHabits()
    }

    private fun syncHabits() {
        viewModelScope.launch { useCases.syncHabitsUseCase() }
    }

    fun onAction(action: HomeActions) {
        when (action) {
            is HomeActions.ChangeDate -> savedStateHandle[DATE_KEY] = action.date
            is HomeActions.OnCompleteHabit -> completeHabit(action.habit)
            else -> Unit
        }
    }

    private fun completeHabit(habit: Habit) {
        if (habit.completedDates.toSet()
                .contains(choosedDate.value.toLocalDate())
        ) {
            return
        }
        viewModelScope.launch {

            val updatedHabit = habit.copy(
                completedDates = habit.completedDates.toMutableList()
                    .apply {
                        add(choosedDate.value.toLocalDate())
                    },
            )
            when (val res = useCases.addHabitUseCase(updatedHabit)) {
                is AppResult.Done -> _event.send(HomeEvents.HabitUpdated)
                is AppResult.Error -> _event.send(HomeEvents.Error(res.error.asUiText()))
            }
        }
    }


}