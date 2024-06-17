package com.mohaberabi.jethabbit.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mohaberabi.jethabbit.core.data.database.entity.HabitSyncEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitSyncDao {


    @Insert
    suspend fun insertSyncHabit(habit: HabitSyncEntity)

    @Query("DELETE FROM habitSync WHERE habitId=:id")
    suspend fun deleteHabitSync(id: String)

    @Query("SELECT * FROM habitsync")
    suspend fun getAllSyncHabits(): List<HabitSyncEntity>

    @Query("DELETE FROM habitSync")
    suspend fun deleteAllPendingHabits()


    @Query("SELECT * FROM habitSync WHERE id=:id")
    suspend fun getPendingHabit(id: String): HabitSyncEntity?

}