package com.mohaberabi.jethabbit.features.settings.data.repository

import com.mohaberabi.jethabbit.core.di.ApplicationScope
import com.mohaberabi.jethabbit.core.domain.alarm.HabitAlarmManager
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.source.JetHabitLocalDataSource
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.auth.domain.source.UserLocalDataSource
import com.mohaberabi.jethabbit.features.auth.domain.source.UserRemoteDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitLocalDataSource
import com.mohaberabi.jethabbit.features.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class DefaultSettingsRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val jetHabitLocalDataSource: JetHabitLocalDataSource,
    private val habitLocalDataSource: HabitLocalDataSource,
    @ApplicationScope private val appScope: CoroutineScope,
    private val alarmManager: HabitAlarmManager,
) : SettingsRepository {
    override suspend fun signOut(): EmptyDataResult<DataError> {
        return try {
            alarmManager.cancelAll()
            val remoteDeferred = appScope.launch { userRemoteDataSource.signOut() }
            val clearPrefsDeferred = appScope.launch { jetHabitLocalDataSource.clear() }
            val clearHabitDeferred = appScope.launch { habitLocalDataSource.deleteAllHabits() }
            joinAll(remoteDeferred, clearPrefsDeferred, clearHabitDeferred)
            AppResult.Done(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }
}