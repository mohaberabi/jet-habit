package com.mohaberabi.jethabbit.core.util.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat


fun Activity.shouldShowPermissionRationale(
    permission: String,
): Boolean =
    shouldShowRequestPermissionRationale(permission)

fun needsNotificationsPermissions(): Boolean = Build.VERSION.SDK_INT >= 33


fun Activity.shouldShowNotificationsRationale(): Boolean =
    if (needsNotificationsPermissions())
        shouldShowPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
    else false

fun Context.hasPermission(
    permission: String,
): Boolean =
    ContextCompat.checkSelfPermission(
        this,
        permission,
    ) == PackageManager.PERMISSION_GRANTED


fun Context.grantedNotificationPermission(): Boolean {
    return if (needsNotificationsPermissions())
        hasPermission(Manifest.permission.POST_NOTIFICATIONS)
    else true
}