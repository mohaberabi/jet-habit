package com.mohaberabi.jethabbit.core.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.provider.ContactsContract.Data
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mohaberabi.jethabbit.core.data.alarm.AndroidJetHabitAlarmManager
import com.mohaberabi.jethabbit.core.data.database.dao.DeletedHabitDao
import com.mohaberabi.jethabbit.core.data.database.dao.HabitDao
import com.mohaberabi.jethabbit.core.data.database.dao.HabitSyncDao
import com.mohaberabi.jethabbit.core.data.database.db.JetHabitDatabase
import com.mohaberabi.jethabbit.core.data.database.typeconvertors.HabitTypeConvertors
import com.mohaberabi.jethabbit.core.data.notification.AndroidHabitNotificationManager
import com.mohaberabi.jethabbit.core.data.source.local.DefaultJetHabitLocalDataSource
import com.mohaberabi.jethabbit.core.domain.alarm.HabitAlarmManager
import com.mohaberabi.jethabbit.core.util.DefaultDispatcherProvider
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import com.mohaberabi.jethabbit.core.util.const.AppConst
import com.mohaberabi.jethabbit.core.util.extensions.dataStore
import com.mohaberabi.jethabbit.core.domain.model.validator.DefaultEmailValidator
import com.mohaberabi.jethabbit.core.domain.model.validator.EmailValidator
import com.mohaberabi.jethabbit.core.domain.model.validator.PasswordValidator
import com.mohaberabi.jethabbit.core.domain.model.validator.SimpleValidator
import com.mohaberabi.jethabbit.core.domain.notification.HabitNotificationManager
import com.mohaberabi.jethabbit.core.domain.source.JetHabitLocalDataSource
import com.mohaberabi.jethabbit.features.auth.domain.repository.UserRepository
import com.mohaberabi.jethabbit.jethabbit.activity.domain.usecase.CheckAuthStateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.internal.processedrootsentinel.ProcessedRootSentinel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider = DefaultDispatcherProvider()

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> =
        context.dataStore


    @Singleton
    @Provides
    fun provideHabitSyncDao(
        db: JetHabitDatabase,
    ): HabitSyncDao = db.habitSyncDao()

    @Singleton
    @Provides
    fun provideDeletedHabitDao(
        db: JetHabitDatabase,
    ): DeletedHabitDao = db.deletedHabitDao()

    @Singleton
    @Provides
    fun provideHabitDao(
        db: JetHabitDatabase,
    ): HabitDao = db.habitDao()

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): JetHabitDatabase =
        Room.databaseBuilder(
            context,
            JetHabitDatabase::class.java,
            AppConst.DB_NAME
        ).addTypeConverter(HabitTypeConvertors())
            .build()

    @Singleton
    @Provides
    @ApplicationScope
    fun provideAppSuperVisorScope(): CoroutineScope = CoroutineScope(SupervisorJob())


    @Singleton
    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun provideEmailValidator(): EmailValidator = DefaultEmailValidator()

    @Singleton
    @Provides
    fun providePasswordValidator(): PasswordValidator = PasswordValidator()

    @Singleton
    @Provides
    fun provideSimpleValidator(): SimpleValidator = SimpleValidator()


    @Singleton
    @Provides
    fun provideCheckAuthUseCase(
        userRepository: UserRepository,
    ): CheckAuthStateUseCase =
        CheckAuthStateUseCase(userRepository)

    @Singleton
    @Provides
    fun provideJetHabitLocalDataSource(
        datastore: DataStore<Preferences>, dispatchers: DispatchersProvider,
    ): JetHabitLocalDataSource =
        DefaultJetHabitLocalDataSource(
            dataStore = datastore,
            dispatchers = dispatchers
        )

    @Singleton
    @Provides
    fun provideAlarmManger(
        @ApplicationContext context: Context,
    ): AlarmManager =
        context.getSystemService(AlarmManager::class.java)

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context,
    ): NotificationManager =
        context.getSystemService(NotificationManager::class.java)

    @Singleton
    @Provides
    fun provideHabitAlarmManager(
        @ApplicationContext context: Context,
    ): HabitAlarmManager = AndroidJetHabitAlarmManager(context)

    @Singleton
    @Provides
    fun provideHabitNotificationManager(
        @ApplicationContext context: Context,
    ): HabitNotificationManager = AndroidHabitNotificationManager(context)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope