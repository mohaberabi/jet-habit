package com.mohaberabi.jethabbit.core.data.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.mohaberabi.jethabbit.R
import com.mohaberabi.jethabbit.core.domain.notification.HabitNotificationManager
import com.mohaberabi.jethabbit.core.util.const.NotificationsConst
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidHabitNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : HabitNotificationManager {


    private val notificationManager: NotificationManager =
        context.getSystemService(NotificationManager::class.java)

    override fun show(
        id: Int,
        title: String,
        body: String,
    ) {
        val noti = NotificationCompat.Builder(
            context,
            NotificationsConst.CHANNEL_ID
        ).setContentText(body)
            .setContentTitle(title).setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground).build()

        notificationManager.notify(id, noti)
    }
}