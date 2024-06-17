package com.mohaberabi.jethabbit.features.auth.domain.source

import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel


interface UserRemoteDataSource {
    suspend fun login(
        email: String,
        password: String,
    ): AppResult<UserModel, DataError>

    suspend fun signup(
        email: String,
        password: String,
        name: String,
        lastname: String
    ): AppResult<UserModel, DataError>

    suspend fun update(user: UserModel): EmptyDataResult<DataError>
    suspend fun signOut()
}