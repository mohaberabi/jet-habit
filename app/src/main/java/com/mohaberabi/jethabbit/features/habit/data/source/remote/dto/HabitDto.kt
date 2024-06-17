package com.mohaberabi.jethabbit.features.habit.data.source.remote.dto

import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.util.extensions.toStartOfDateTimestamp
import com.mohaberabi.jethabbit.core.util.extensions.toTimeStamp
import com.mohaberabi.jethabbit.core.util.extensions.toZonedDateTime
import java.time.DayOfWeek


data class HabitDto(
    val id: String,
    val reminder: Long,
    val frequency: List<String>,
    val completedDates: List<Long>,
    val name: String
) {
    constructor() : this("", 0L, listOf(), listOf(), "")
}

fun Habit.toHabitDto(): HabitDto = HabitDto(
    id = id,
    name = name,
    reminder = reminder.toZonedDateTime().toTimeStamp(),
    frequency = frequency.map { it.name },
    completedDates = completedDates.map { it.toZonedDateTime().toTimeStamp() }
)

fun HabitDto.toHabit() =
    Habit(
        id = id,
        reminder = this.reminder.toZonedDateTime().toLocalTime(),
        name = name,
        frequency = frequency.map { DayOfWeek.valueOf(it) },
        completedDates = this.completedDates.map { it.toZonedDateTime().toLocalDate() }
    )