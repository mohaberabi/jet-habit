package com.mohaberabi.jethabbit.features.habit.presentation.detail.viewmodel

import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.presentation.compose.TimePickerResult
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.UUID

data class HabitDetailsState(
    val habitName: String = "",
    val habitId: String = UUID.randomUUID().toString(),
    val habitReminder: TimePickerResult = TimePickerResult(),
    val frequencies: Set<DayOfWeek> = setOf(),
    val loading: Boolean = false,
    val startDate: ZonedDateTime = ZonedDateTime.now(),
    val completedDates: List<LocalDate> = emptyList(),
    val showTimePicker: Boolean = false,
    val showDeleteButton: Boolean = false,
    val showDeleteDialog: Boolean = false,
) {
    companion object {
        fun fromHabit(habit: Habit): HabitDetailsState {
            return HabitDetailsState(
                habitName = habit.name,
                habitId = habit.id,
                habitReminder = TimePickerResult(habit.reminder.hour, habit.reminder.minute),
                loading = false,
                showDeleteButton = true,
                showTimePicker = false,
                completedDates = habit.completedDates,
                frequencies = habit.frequency.toSet()
            )
        }
    }

    val constructedHabit: Habit = Habit(
        id = habitId,
        name = habitName,
        reminder = reminderTime,
        frequency = frequencies.toList(),
        completedDates = completedDates
    )

    private val reminderTime: LocalTime
        get() = LocalTime.of(
            habitReminder.hour,
            habitReminder.minute
        )

    val canAdd: Boolean
        get() = habitName.isNotEmpty() && frequencies.isNotEmpty()
}
