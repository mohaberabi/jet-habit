package com.mohaberabi.jethabbit.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mohaberabi.jethabbit.core.data.database.entity.DeletedHabitEntity
import com.mohaberabi.jethabbit.core.data.database.entity.HabitSyncEntity

@Dao
interface DeletedHabitDao {
    @Insert
    suspend fun insertDeletedHabit(habit: DeletedHabitEntity)

    @Query("DELETE FROM deletedHabit WHERE habitId=:id")
    suspend fun deleteDeletedHabit(id: String)

    @Query("SELECT * FROM deletedHabit")
    suspend fun getAllDeletedHabits(): List<DeletedHabitEntity>

    @Query("DELETE FROM deletedHabit")
    suspend fun deleteAllDeletedHabits()


    @Query("SELECT * FROM deletedHabit WHERE habitId=:id")
    suspend fun getDeletedHabit(id: String): DeletedHabitEntity?


}


