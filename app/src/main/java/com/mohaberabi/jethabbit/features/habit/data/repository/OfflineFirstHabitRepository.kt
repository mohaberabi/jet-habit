package com.mohaberabi.jethabbit.features.habit.data.repository

import com.mohaberabi.jethabbit.core.di.ApplicationScope
import com.mohaberabi.jethabbit.core.domain.alarm.HabitAlarmManager
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.domain.model.asEmptyResult
import com.mohaberabi.jethabbit.core.domain.model.fold
import com.mohaberabi.jethabbit.core.domain.model.foldAndReturn
import com.mohaberabi.jethabbit.core.domain.model.foldWithResult
import com.mohaberabi.jethabbit.core.domain.model.map
import com.mohaberabi.jethabbit.core.domain.model.toPendingHabit
import com.mohaberabi.jethabbit.core.domain.model.toReminder
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.core.util.extensions.toStartOfDateTimestamp
import com.mohaberabi.jethabbit.features.auth.domain.source.UserLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.repository.HabitRepository
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.remote.HabitRemoteDataSource
import com.mohaberabi.jethabbit.features.habit.domain.sync.HabitSyncEnqueue
import com.mohaberabi.jethabbit.features.habit.domain.sync.HabitSyncType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import javax.inject.Inject

class OfflineFirstHabitRepository @Inject constructor(
    private val habitRemoteDataSource: HabitRemoteDataSource,
    private val habitLocalDataSource: HabitLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    @ApplicationScope private val appScope: CoroutineScope,
    private val alarmManager: HabitAlarmManager,
    private val dispatchers: DispatchersProvider,
    private val habitSyncEnqueue: HabitSyncEnqueue,
) : HabitRepository {
    override fun getAllHabitsByDayOfWeek(dayOfWeek: String): Flow<List<Habit>> =
        habitLocalDataSource.getAllHabitsByDayOfWeek(dayOfWeek)

    override suspend fun fetchAndSaveHabits(): EmptyDataResult<DataError> {
        return withContext(dispatchers.io) {
            val localHabitsSize = habitLocalDataSource.getHabitsSize()
            if (localHabitsSize == 0) {
                val uid = userLocalDataSource.getUserData().first().uid
                when (val remoteRes = habitRemoteDataSource.getAllHabits(uid = uid)) {
                    is AppResult.Done -> {
                        val habits = remoteRes.data
                        val localDeferred = appScope.launch {
                            habitLocalDataSource.addAllHabits(habits)
                        }
                        val alarmDeferred = appScope.launch {
                            for (habit in habits) {
                                alarmManager.scheduale(habit.toReminder())
                            }
                        }
                        joinAll(alarmDeferred, localDeferred)
                        AppResult.Done(Unit)
                    }

                    is AppResult.Error -> remoteRes.asEmptyResult()
                }
            } else {
                AppResult.Done(Unit)
            }
        }

    }


    override suspend fun getHabitById(
        id: String,
    ): Habit? = habitLocalDataSource.getHabitById(id)

    override suspend fun deleteHabit(
        habit: Habit,
    ): EmptyDataResult<DataError> {
        habitLocalDataSource.deleteHabit(habit.id)
        val uid = userLocalDataSource.getUserData().first().uid
        val res = habitRemoteDataSource.deleteHabit(uid = uid, id = habit.id)
        when (res) {
            is AppResult.Done -> {
                appScope.launch {
                    alarmManager.cancel(habit.toReminder())
                }.join()
            }

            is AppResult.Error -> {
                appScope.launch {
                    habitSyncEnqueue.enqueue(
                        HabitSyncType.DeleteHabit(
                            habit = habit,
                            uid = uid,
                        )
                    )
                }.join()
            }
        }
        return AppResult.Done(Unit)

    }

    override suspend fun addHabit(
        habit: Habit,
    ): EmptyDataResult<DataError> {
        val localRes = habitLocalDataSource.addHabit(habit)

        if (localRes !is AppResult.Done) {
            return localRes.asEmptyResult()
        }
        val uid = userLocalDataSource.getUserData().first().uid
        val remoteRes = habitRemoteDataSource.addHabit(habit, uid)
        when (remoteRes) {
            is AppResult.Done -> {
                appScope.launch {
                    alarmManager.scheduale(habit.toReminder())
                }.join()
            }

            is AppResult.Error -> {
                appScope.launch {
                    habitSyncEnqueue.enqueue(
                        HabitSyncType.UpsertHabit(
                            habit = habit, uid = uid,
                        )
                    )
                }.join()
            }
        }
        return AppResult.Done(Unit)

    }


}