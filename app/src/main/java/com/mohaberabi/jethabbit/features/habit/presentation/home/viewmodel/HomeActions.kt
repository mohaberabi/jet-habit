package com.mohaberabi.jethabbit.features.habit.presentation.home.viewmodel

import com.mohaberabi.jethabbit.core.domain.model.Habit
import java.time.ZonedDateTime

sealed interface HomeActions {

    data class ChangeDate(val date: ZonedDateTime) : HomeActions
    data class OnCompleteHabit(val habit: Habit) : HomeActions
    data class OnHabitClick(val id: String?) : HomeActions
}