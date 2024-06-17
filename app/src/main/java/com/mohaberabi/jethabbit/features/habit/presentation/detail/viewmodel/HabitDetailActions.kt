package com.mohaberabi.jethabbit.features.habit.presentation.detail.viewmodel

import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.presentation.compose.TimePickerResult
import java.time.DayOfWeek


sealed interface HabitDetailActions {


    data class NameChanged(val name: String) : HabitDetailActions
    data class FrequencyChanged(val dayOfWeek: DayOfWeek) : HabitDetailActions
    data class TimeChanged(val timePickerResult: TimePickerResult) : HabitDetailActions
    data object AddHabit : HabitDetailActions

    data object ToggleTimePicker : HabitDetailActions

    data object DeleteHabit : HabitDetailActions
    data object ToggleDeleteDialog : HabitDetailActions
}