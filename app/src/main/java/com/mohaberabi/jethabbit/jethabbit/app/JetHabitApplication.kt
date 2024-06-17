package com.mohaberabi.jethabbit.jethabbit.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkerFactory
import com.mohaberabi.jethabbit.core.domain.notification.HabitNotificationManager
import com.mohaberabi.jethabbit.core.util.const.NotificationsConst
import com.mohaberabi.jethabbit.core.util.extensions.needsNotificationsPermissions
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class JetHabitApplication : Application(), Configuration.Provider {
    private lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NotificationManager::class.java)
        createNotificationChanel()
    }

    private fun createNotificationChanel() {
        if (needsNotificationsPermissions()) {
            val channel = NotificationChannel(
                NotificationsConst.CHANNEL_ID,
                NotificationsConst.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setWorkerFactory(workerFactory).build()

}