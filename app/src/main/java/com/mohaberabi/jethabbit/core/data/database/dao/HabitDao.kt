package com.mohaberabi.jethabbit.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.jethabbit.core.data.database.entity.HabitEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitDao {


    @Upsert
    suspend fun insertAllHabits(habits: List<HabitEntity>)

    @Upsert
    suspend fun insertHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE frequencies LIKE '%' || :dayOfWeek || '%' ORDER BY id ASC")
    fun getAllHabitsByDayOfWeek(dayOfWeek: String): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id =:id")
    suspend fun getHabitById(id: String): HabitEntity?

    @Query("DELETE FROM habits")
    suspend fun deleteAllHabits()

    @Query("DELETE  FROM habits WHERE id=:id")
    suspend fun deleteHabit(id: String)

    @Query("SELECT COUNT(*) FROM habits")
    suspend fun getHabitsCount(): Int

}