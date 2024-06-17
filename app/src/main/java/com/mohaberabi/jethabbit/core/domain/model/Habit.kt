package com.mohaberabi.jethabbit.core.domain.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime


data class Habit(
    val id: String,
    val name: String,
    val frequency: List<DayOfWeek>,
    val completedDates: List<LocalDate> = listOf(),
    val reminder: LocalTime,
)


fun Habit.toPendingHabit(uid: String): PendingHabitModel =
    PendingHabitModel(habitId = id, uid = uid, habit = this)