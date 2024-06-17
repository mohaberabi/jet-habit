package com.mohaberabi.jethabbit.core.domain.model

import java.time.DayOfWeek


data class ReminderDay(
    val id: Int,
    val dayOfWeek: DayOfWeek
)

data class HabitReminderModel(
    val habitId: String,
    val days: List<ReminderDay>,
    val name: String,
    val hour: Int,
    val minute: Int,
)

fun Habit.toReminder(): HabitReminderModel {
    val baseline = id.hashCode() * 10
    return HabitReminderModel(
        habitId = id,
        days = frequency.map { ReminderDay(id = it.ordinal * baseline, dayOfWeek = it) },
        hour = reminder.hour,
        minute = reminder.minute,
        name = name
    )
}