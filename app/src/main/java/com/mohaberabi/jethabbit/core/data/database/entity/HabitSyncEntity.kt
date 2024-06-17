package com.mohaberabi.jethabbit.core.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.jethabbit.core.domain.model.PendingHabitModel


@Entity("habitSync")
data class HabitSyncEntity(
    @PrimaryKey(autoGenerate = false)
    val habitId: String,
    val uid: String,
    @Embedded val habit: HabitEntity,
)


@Entity("deletedHabit")
data class DeletedHabitEntity(
    @PrimaryKey(autoGenerate = false)
    val habitId: String,
    val uid: String,
    @Embedded val habit: HabitEntity,
)


fun PendingHabitModel.toPendingEntity(
): HabitSyncEntity = HabitSyncEntity(
    habitId = habitId,
    uid = uid,
    habit = habit.toHabitEntity(),
)

fun PendingHabitModel.toDeletedEntity(
): DeletedHabitEntity = DeletedHabitEntity(
    habitId = habitId,
    uid = uid,
    habit = habit.toHabitEntity(),
)


fun HabitSyncEntity.toPendingModel(
): PendingHabitModel = PendingHabitModel(
    habitId = habitId,
    uid = uid,
    habit = habit.toHabit(),
)

fun DeletedHabitEntity.toPendingModel(
): PendingHabitModel = PendingHabitModel(
    habitId = habitId,
    uid = uid,
    habit = habit.toHabit(),
)