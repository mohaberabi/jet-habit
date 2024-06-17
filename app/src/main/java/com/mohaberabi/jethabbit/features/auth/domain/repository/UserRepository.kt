package com.mohaberabi.jethabbit.features.auth.domain.repository

import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {


    suspend fun login(
        email: String,
        password: String,
    ): EmptyDataResult<DataError>

    suspend fun signup(
        email: String,
        password: String,
        name: String,
        lastname: String
    ): EmptyDataResult<DataError>

    suspend fun update(user: UserModel): EmptyDataResult<DataError>

    fun getUserData(): Flow<UserModel>
}