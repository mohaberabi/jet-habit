package com.mohaberabi.jethabbit.features.habit.data.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.jethabbit.core.data.database.dao.DeletedHabitDao
import com.mohaberabi.jethabbit.core.data.database.dao.HabitDao
import com.mohaberabi.jethabbit.core.data.database.dao.HabitSyncDao
import com.mohaberabi.jethabbit.core.di.ApplicationScope
import com.mohaberabi.jethabbit.core.domain.alarm.HabitAlarmManager
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import com.mohaberabi.jethabbit.features.auth.domain.source.UserLocalDataSource
import com.mohaberabi.jethabbit.features.habit.data.repository.OfflineFirstHabitRepository
import com.mohaberabi.jethabbit.features.habit.data.source.local.RoomDeletedHabitLocalDataSource
import com.mohaberabi.jethabbit.features.habit.data.source.local.RoomHabitLocalDataSource
import com.mohaberabi.jethabbit.features.habit.data.source.local.RoomPendingHabitLocalDataSource
import com.mohaberabi.jethabbit.features.habit.data.source.remote.FirebaseHabitRemoteDataSource
import com.mohaberabi.jethabbit.features.habit.data.sync.DefaultHabitSyncEnqueuer
import com.mohaberabi.jethabbit.features.habit.domain.repository.HabitRepository
import com.mohaberabi.jethabbit.features.habit.domain.source.local.DeletedHabitLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitSyncLocalDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.remote.HabitRemoteDataSource
import com.mohaberabi.jethabbit.features.habit.domain.sync.HabitSyncEnqueue
import com.mohaberabi.jethabbit.features.habit.domain.usecase.AddHabitUseCase
import com.mohaberabi.jethabbit.features.habit.domain.usecase.DeleteHabitUseCase
import com.mohaberabi.jethabbit.features.habit.domain.usecase.GetHabitByIdUseCase
import com.mohaberabi.jethabbit.features.habit.domain.usecase.GetHabitsByDayOfWeekUseCase
import com.mohaberabi.jethabbit.features.habit.domain.usecase.HabitUseCases
import com.mohaberabi.jethabbit.features.habit.domain.usecase.SyncHabitsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HabitModule {
    @Singleton
    @Provides
    fun provideHabitUseCases(
        habitRepository: HabitRepository,
    ): HabitUseCases = HabitUseCases(
        getHabitByIdUseCase = GetHabitByIdUseCase(habitRepository),
        addHabitUseCase = AddHabitUseCase(habitRepository),
        getHabitsByDayOfWeekUseCase = GetHabitsByDayOfWeekUseCase(habitRepository),
        deleteHabitUseCase = DeleteHabitUseCase(habitRepository),
        syncHabitsUseCase = SyncHabitsUseCase(habitRepository)
    )

    @Singleton
    @Provides
    fun provideHabitRemoteDataSource(
        firestore: FirebaseFirestore
    ): HabitRemoteDataSource = FirebaseHabitRemoteDataSource(firestore)

    @Singleton
    @Provides
    fun provideHabitLocalDataSource(
        habitDao: HabitDao,
    ): HabitLocalDataSource = RoomHabitLocalDataSource(habitDao)


    @Singleton
    @Provides

    fun provideDeletedHabitLocalDataSource(
        deletedHabitDao: DeletedHabitDao
    ): DeletedHabitLocalDataSource =
        RoomDeletedHabitLocalDataSource(deletedHabitDao)


    @Singleton
    @Provides

    fun providePendingHabitLocalDataSource(
        habitSyncDao: HabitSyncDao
    ): HabitSyncLocalDataSource =
        RoomPendingHabitLocalDataSource(habitSyncDao)


    @Singleton
    @Provides
    fun provideHabitSyncEnqueue(
        @ApplicationScope appScope: CoroutineScope,
        habitSyncLocalDataSource: HabitSyncLocalDataSource,
        deleteHabitLocalDataSource: DeletedHabitLocalDataSource,
        dispatchers: DispatchersProvider,
        @ApplicationContext context: Context
    ): HabitSyncEnqueue = DefaultHabitSyncEnqueuer(
        deletedHabitLocalDataSource = deleteHabitLocalDataSource,
        dispatchers = dispatchers,
        habitSyncLocalDataSource = habitSyncLocalDataSource,
        appScope = appScope,
        context = context,
    )

    @Singleton
    @Provides
    fun provideHabitRepository(
        habitRemoteDataSource: HabitRemoteDataSource,
        habitLocalDataSource: HabitLocalDataSource,
        @ApplicationScope appScope: CoroutineScope,
        userLocalDataSource: UserLocalDataSource,
        alarmManager: HabitAlarmManager,
        dispatchers: DispatchersProvider,
        habitSyncEnqueue: HabitSyncEnqueue
    ): HabitRepository = OfflineFirstHabitRepository(
        habitLocalDataSource = habitLocalDataSource,
        habitRemoteDataSource = habitRemoteDataSource,
        appScope = appScope,
        userLocalDataSource = userLocalDataSource,
        alarmManager = alarmManager,
        dispatchers = dispatchers,
        habitSyncEnqueue = habitSyncEnqueue
    )

}