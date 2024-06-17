package com.mohaberabi.jethabbit.core.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.mohaberabi.jethabbit.core.domain.alarm.HabitAlarmConst
import com.mohaberabi.jethabbit.core.domain.alarm.HabitAlarmManager
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.domain.model.HabitReminderModel
import com.mohaberabi.jethabbit.core.util.extensions.toTimeStamp
import com.mohaberabi.jethabbit.core.util.extensions.toZonedDateTime
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Clock
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.TimeZone
import javax.inject.Inject

class AndroidJetHabitAlarmManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : HabitAlarmManager {
    companion object {
        const val NEXT_WEEK: Long = 7 * 24 * 60 * 60 * 1000
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun cancelAll() {
        alarmManager.cancelAll()
    }

    private val alarmManager: AlarmManager = context.getSystemService(AlarmManager::class.java)
    override fun scheduale(
        reminder: HabitReminderModel,
    ) {

        try {
            for (day in reminder.days) {
                val triggerAt =
                    getDayTimestamp(
                        day = day.dayOfWeek,
                        hour = reminder.hour,
                        minute = reminder.minute
                    )

                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    triggerAt,
                    NEXT_WEEK,
                    createPendingIntent(code = day.id, body = reminder.name),
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun cancel(
        reminder: HabitReminderModel,
    ) {
        try {
            for (day in reminder.days) {
                alarmManager.cancel(
                    createPendingIntent(
                        code = day.id,
                        body = reminder.name
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun createPendingIntent(
        code: Int,
        body: String = "",
    ): PendingIntent {
        val intent = Intent(
            context,
            HabitAlarmReciever::class.java,
        ).apply {
            putExtra(
                HabitAlarmConst.DEFAULT_HABIT_TTL_KEY,
                "Your Habit Reminder...."
            )
            putExtra(
                HabitAlarmConst.HABIT_NAME_KEY,
                body
            )
            putExtra(
                HabitAlarmConst.HABIT_ID_KEY,
                code
            )
        }
        val pending = PendingIntent.getBroadcast(
            context,
            code,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return pending
    }

    private fun getDayTimestamp(
        day: DayOfWeek,
        hour: Int = 0,
        minute: Int = 0
    ): Long {
        val now = LocalDateTime.now()
        var next = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0)
        while (next.dayOfWeek != day || next.isBefore(now)) {
            next = next.plusDays(1)
        }
        return next.toLocalDate().toZonedDateTime().toTimeStamp()

    }


}