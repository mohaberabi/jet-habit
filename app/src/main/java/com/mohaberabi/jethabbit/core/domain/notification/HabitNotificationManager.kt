package com.mohaberabi.jethabbit.core.domain.notification

interface HabitNotificationManager {
    fun show(
        id: Int,
        title: String,
        body: String,
    )
}