package com.mohaberabi.jethabbit.features.auth.data.soruce.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.const.EndPoints
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.core.util.error.fromFirebaseAuthException
import com.mohaberabi.jethabbit.core.util.error.fromFirebaseFirestoreException
import com.mohaberabi.jethabbit.features.auth.data.soruce.remote.dto.UserModelDto
import com.mohaberabi.jethabbit.features.auth.data.soruce.remote.dto.toUserDto
import com.mohaberabi.jethabbit.features.auth.data.soruce.remote.dto.toUserModel
import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel
import com.mohaberabi.jethabbit.features.auth.domain.source.UserRemoteDataSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : UserRemoteDataSource {
    override suspend fun login(
        email: String,
        password: String
    ): AppResult<UserModel, DataError> {
        return try {
            val authedUser = auth.signInWithEmailAndPassword(email, password).await().user
            authedUser?.let { user ->
                val currentUserData =
                    users.document(user.uid).get().await().toObject(UserModelDto::class.java)
                currentUserData?.let { dto ->
                    AppResult.Done(dto.toUserModel())
                } ?: AppResult.Error(DataError.Authentication.USER_NOT_FOUND)
            } ?: AppResult.Error(DataError.Authentication.USER_NOT_FOUND)
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseAuthException())
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Authentication.UNKNOWN_ERROR)
        }
    }

    override suspend fun signup(
        email: String,
        password: String,
        name: String,
        lastname: String
    ): AppResult<UserModel, DataError> {
        return try {
            val authedUser = auth.createUserWithEmailAndPassword(email, password).await().user
            authedUser?.let {
                val userDto = UserModelDto(
                    email = email,
                    uid = authedUser.uid,
                    name = name,
                    lastname = lastname,
                )
                users.document(userDto.uid).set(userDto).await()
                AppResult.Done(userDto.toUserModel())
            } ?: AppResult.Error(DataError.Authentication.UNKNOWN_ERROR)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseAuthException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Authentication.UNKNOWN_ERROR)
        }
    }

    override suspend fun update(
        user: UserModel,
    ): EmptyDataResult<DataError> {
        return try {
            users
                .document(user.uid)
                .set(user.toUserDto())
                .await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Authentication.UNKNOWN_ERROR)
        }
    }

    override suspend fun signOut() {
        try {
            auth.signOut()
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
        }
    }

    private val users: CollectionReference = firestore.collection(EndPoints.USERS)
}
