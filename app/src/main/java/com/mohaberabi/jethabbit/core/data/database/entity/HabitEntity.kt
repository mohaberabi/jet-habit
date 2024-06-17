package com.mohaberabi.jethabbit.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.util.extensions.toStartOfDateTimestamp
import com.mohaberabi.jethabbit.core.util.extensions.toTimeStamp
import com.mohaberabi.jethabbit.core.util.extensions.toZonedDateTime
import java.time.DayOfWeek

@Entity("habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val frequencies: String,
    val completedDays: List<Long>,
    val reminder: Long,
)

fun Habit.toHabitEntity(): HabitEntity = HabitEntity(
    id = id,
    name = name,
    frequencies = frequency.joinToString(","),
    reminder = reminder.toZonedDateTime().toTimeStamp(),
    completedDays = completedDates.map { it.toZonedDateTime().toTimeStamp() }
)

fun HabitEntity.toHabit(): Habit = Habit(
    id = id,
    name = name,
    frequency = frequencies.split(",").map { DayOfWeek.valueOf(it) },
    reminder = reminder.toZonedDateTime().toLocalTime(),
    completedDates = completedDays.map { it.toZonedDateTime().toLocalDate() }
)