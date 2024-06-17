package com.mohaberabi.jethabbit.features.auth.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import com.mohaberabi.jethabbit.core.domain.model.validator.EmailValidator
import com.mohaberabi.jethabbit.core.domain.model.validator.PasswordValidator
import com.mohaberabi.jethabbit.core.domain.model.validator.SimpleValidator
import com.mohaberabi.jethabbit.features.auth.data.repository.DefaultUserRepository
import com.mohaberabi.jethabbit.features.auth.data.soruce.local.UserDataStore
import com.mohaberabi.jethabbit.features.auth.data.soruce.remote.FirebaseUserRemoteDataSource
import com.mohaberabi.jethabbit.features.auth.domain.repository.UserRepository
import com.mohaberabi.jethabbit.features.auth.domain.source.UserLocalDataSource
import com.mohaberabi.jethabbit.features.auth.domain.source.UserRemoteDataSource
import com.mohaberabi.jethabbit.features.auth.domain.usecase.AuthUseCases
import com.mohaberabi.jethabbit.features.auth.domain.usecase.AuthUseCases_Factory
import com.mohaberabi.jethabbit.features.auth.domain.usecase.SignInUseCase
import com.mohaberabi.jethabbit.features.auth.domain.usecase.SignUpUseCase
import com.mohaberabi.jethabbit.features.auth.domain.usecase.UserValidationUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): UserRemoteDataSource = FirebaseUserRemoteDataSource(
        auth = auth,
        firestore = firestore
    )

    @Singleton
    @Provides
    fun provideUserLocalDataSource(
        dataStore: DataStore<Preferences>,
        dispatchers: DispatchersProvider,
    ): UserLocalDataSource = UserDataStore(
        datastore = dataStore,
        dispatchers = dispatchers
    )

    @Singleton
    @Provides
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository = DefaultUserRepository(
        userRemoteDataSource = userRemoteDataSource,
        userLocalDataSource = userLocalDataSource
    )


    @Singleton
    @Provides
    fun provideUserValidators(
        emailValidator: EmailValidator,
        passwordValidator: PasswordValidator,
        simpleValidator: SimpleValidator,
    ): UserValidationUseCases = UserValidationUseCases(
        simpleValidator = simpleValidator,
        emailValidator = emailValidator,
        passwordValidator = passwordValidator
    )

    @Singleton
    @Provides
    fun provideAuthUseCases(
        userRepository: UserRepository,
    ): AuthUseCases = AuthUseCases(
        signInUseCase = SignInUseCase(userRepository),
        signUpUseCase = SignUpUseCase(userRepository),
    )
}