package com.mohaberabi.jethabbit.features.settings.di

import com.mohaberabi.jethabbit.core.di.ApplicationScope
import com.mohaberabi.jethabbit.core.domain.source.JetHabitLocalDataSource
import com.mohaberabi.jethabbit.features.auth.domain.repository.UserRepository
import com.mohaberabi.jethabbit.features.auth.domain.source.UserLocalDataSource
import com.mohaberabi.jethabbit.features.auth.domain.source.UserRemoteDataSource
import com.mohaberabi.jethabbit.features.habit.domain.source.local.HabitLocalDataSource
import com.mohaberabi.jethabbit.features.settings.data.repository.DefaultSettingsRepository
import com.mohaberabi.jethabbit.features.settings.domain.repository.SettingsRepository
import com.mohaberabi.jethabbit.features.settings.domain.usecase.GetUserDataUseCase
import com.mohaberabi.jethabbit.features.settings.domain.usecase.SignOutUseCase
import com.mohaberabi.jethabbit.features.settings.domain.usecase.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Singleton
    @Provides
    fun provideSignOutUseCase(
        settingsRepository: SettingsRepository,
    ): SignOutUseCase = SignOutUseCase(
        settingsRepository = settingsRepository,
    )

    @Singleton
    @Provides
    fun provideUpdateUserUseCase(
        userRepository: UserRepository
    ): UpdateUserUseCase = UpdateUserUseCase(userRepository)


    @Singleton
    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository
    ): GetUserDataUseCase = GetUserDataUseCase(userRepository)


    @Singleton
    @Provides
    fun provideSettingsRepository(
        userRemoteDataSource: UserRemoteDataSource,
        jetHabitLocalDataSource: JetHabitLocalDataSource,
        @ApplicationScope appScope: CoroutineScope,
        habitLocalDataSource: HabitLocalDataSource
    ): SettingsRepository = DefaultSettingsRepository(
        userRemoteDataSource = userRemoteDataSource,
        jetHabitLocalDataSource = jetHabitLocalDataSource,
        appScope = appScope,
        habitLocalDataSource = habitLocalDataSource

    )
}