package com.mohaberabi.jethabbit.features.habit.presentation.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.presentation.compose.TimePickerResult
import com.mohaberabi.jethabbit.core.util.error.asUiText
import com.mohaberabi.jethabbit.features.habit.domain.usecase.HabitUseCases
import com.mohaberabi.jethabbit.features.habit.presentation.detail.navigation.HabitDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HabitDetailViewModel @Inject constructor(
    private val habitUseCases: HabitUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val _event = Channel<HabitDetailEvents>()
    val event = _event.receiveAsFlow()
    private val _state = MutableStateFlow(HabitDetailsState())
    val state = _state.asStateFlow()
    private val habitId: String? = savedStateHandle.toRoute<HabitDetailRoute>().id

    init {
        getHabit()
    }

    fun onAction(action: HabitDetailActions) {
        when (action) {
            HabitDetailActions.AddHabit -> addHabit()
            is HabitDetailActions.FrequencyChanged -> frequencyChanged(action.dayOfWeek)
            is HabitDetailActions.NameChanged -> onNameChanged(action.name)
            is HabitDetailActions.TimeChanged -> onTimeChanged(action.timePickerResult)
            HabitDetailActions.ToggleTimePicker -> toggleTimePicker()
            HabitDetailActions.ToggleDeleteDialog -> toggleDeleteDialog()
            HabitDetailActions.DeleteHabit -> deleteHabit()
        }
    }


    private fun deleteHabit() {
        _state.update { it.copy(loading = true, showDeleteDialog = false) }

        viewModelScope.launch {
            when (val res = habitUseCases.deleteHabitUseCase(_state.value.constructedHabit)) {
                is AppResult.Done -> _event.send(HabitDetailEvents.HabitDeleted)
                is AppResult.Error -> {
                    _event.send(HabitDetailEvents.Error(res.error.asUiText()))
                    _state.update { it.copy(loading = false) }
                }
            }
        }
    }

    private fun toggleDeleteDialog() =
        _state.update { it.copy(showDeleteDialog = !it.showDeleteDialog) }

    private fun toggleTimePicker() = _state.update { it.copy(showTimePicker = !it.showTimePicker) }

    private fun frequencyChanged(day: DayOfWeek) {
        _state.update {
            it.copy(
                frequencies = it.frequencies.toMutableSet().apply {
                    if (contains(day)) remove(day) else add(day)
                }
            )
        }
    }

    private fun onTimeChanged(time: TimePickerResult) =
        _state.update { it.copy(habitReminder = time) }

    private fun onNameChanged(name: String) = _state.update { it.copy(habitName = name) }


    private fun addHabit() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val res = habitUseCases.addHabitUseCase(_state.value.constructedHabit)) {
                is AppResult.Done -> _event.send(HabitDetailEvents.HabitAdded)
                is AppResult.Error -> _event.send(HabitDetailEvents.Error(res.error.asUiText()))
            }
        }
    }

    private fun getHabit() {

        habitId?.let { id ->
            _state.update { it.copy(loading = true) }
            viewModelScope.launch {
                val habit = habitUseCases.getHabitByIdUseCase(id)
                habit?.let { choosedHabit ->
                    _state.update { HabitDetailsState.fromHabit(choosedHabit) }
                }
            }
            _state.update { it.copy(loading = false) }
        }

    }
}