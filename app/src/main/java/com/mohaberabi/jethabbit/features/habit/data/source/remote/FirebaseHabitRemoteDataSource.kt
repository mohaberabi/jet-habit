package com.mohaberabi.jethabbit.features.habit.data.source.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.util.const.EndPoints
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.core.util.error.fromFirebaseFirestoreException
import com.mohaberabi.jethabbit.features.habit.data.source.remote.dto.HabitDto
import com.mohaberabi.jethabbit.features.habit.data.source.remote.dto.toHabit
import com.mohaberabi.jethabbit.features.habit.data.source.remote.dto.toHabitDto
import com.mohaberabi.jethabbit.features.habit.domain.source.remote.HabitRemoteDataSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseHabitRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) : HabitRemoteDataSource {
    override suspend fun getAllHabits(
        uid: String,
    ): AppResult<List<Habit>, DataError> {
        return try {
            val habits = habitsCol(uid).get().await().map { it.toObject<HabitDto>().toHabit() }
            AppResult.Done(habits)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        }
    }

    override suspend fun addHabit(
        habit: Habit,
        uid: String,
    ): EmptyDataResult<DataError> {
        return try {
            habitsCol(uid).document(habit.id).set(habit.toHabitDto()).await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())

        }
    }


    override suspend fun deleteHabit(id: String, uid: String): EmptyDataResult<DataError> {
        return try {
            habitsCol(uid).document(id).delete().await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        }
    }

    private fun habitsCol(uid: String): CollectionReference =
        firestore.collection(EndPoints.USERS).document(uid).collection(EndPoints.HABITS)

}