package com.mohaberabi.jethabbit.features.auth.domain.source

import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {


    suspend fun saveUser(user: UserModel): EmptyDataResult<DataError>
    suspend fun clearUser()
    suspend fun updateUser(user: UserModel): EmptyDataResult<DataError>
    fun getUserData(): Flow<UserModel>
}