package com.mohaberabi.jethabbit.core.data.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mohaberabi.jethabbit.core.domain.alarm.HabitAlarmConst
import com.mohaberabi.jethabbit.core.domain.notification.HabitNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HabitAlarmReciever : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager: HabitNotificationManager
    override fun onReceive(context: Context?, intent: Intent?) {
        val title =
            intent?.getStringExtra(HabitAlarmConst.DEFAULT_HABIT_TTL_KEY) ?: "Your Habit Reminder "
        val body = intent?.getStringExtra(HabitAlarmConst.HABIT_NAME_KEY) ?: "Habit"
        val id = intent?.getIntExtra(HabitAlarmConst.HABIT_ID_KEY, 1) ?: 1
        notificationManager.show(
            title = title,
            body = body,
            id = id
        )
    }

}