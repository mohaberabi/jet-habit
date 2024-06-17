package com.mohaberabi.jethabbit.core.domain.model

data class PendingHabitModel(
    val habitId: String,
    val uid: String,
    val habit: Habit,
)
