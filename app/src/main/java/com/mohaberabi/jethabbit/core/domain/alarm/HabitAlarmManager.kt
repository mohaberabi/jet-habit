package com.mohaberabi.jethabbit.core.domain.alarm

import com.mohaberabi.jethabbit.core.domain.model.HabitReminderModel


interface HabitAlarmManager {

    fun scheduale(
        reminder: HabitReminderModel
    )

    fun cancel(
        reminder: HabitReminderModel,
    )

    fun cancelAll()


}