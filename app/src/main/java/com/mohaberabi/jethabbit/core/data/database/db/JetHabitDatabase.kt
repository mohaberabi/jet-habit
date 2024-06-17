package com.mohaberabi.jethabbit.core.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohaberabi.jethabbit.core.data.database.dao.DeletedHabitDao
import com.mohaberabi.jethabbit.core.data.database.dao.HabitDao
import com.mohaberabi.jethabbit.core.data.database.dao.HabitSyncDao
import com.mohaberabi.jethabbit.core.data.database.entity.DeletedHabitEntity
import com.mohaberabi.jethabbit.core.data.database.entity.HabitEntity
import com.mohaberabi.jethabbit.core.data.database.entity.HabitSyncEntity
import com.mohaberabi.jethabbit.core.data.database.typeconvertors.HabitTypeConvertors


@Database(
    entities = [
        HabitEntity::class,
        HabitSyncEntity::class,
        DeletedHabitEntity::class
    ],
    version = 1,
    exportSchema = false,
)

@TypeConverters(
    HabitTypeConvertors::class
)
abstract class JetHabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitSyncDao(): HabitSyncDao
    abstract fun deletedHabitDao(): DeletedHabitDao
}