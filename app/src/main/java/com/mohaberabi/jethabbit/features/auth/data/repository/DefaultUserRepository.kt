package com.mohaberabi.jethabbit.features.auth.data.repository

import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel
import com.mohaberabi.jethabbit.features.auth.domain.repository.UserRepository
import com.mohaberabi.jethabbit.features.auth.domain.source.UserLocalDataSource
import com.mohaberabi.jethabbit.features.auth.domain.source.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {
    override suspend fun login(
        email: String, password: String,
    ): EmptyDataResult<DataError> {
        val remoteRes = userRemoteDataSource.login(
            email = email,
            password = password,
        )
        return when (remoteRes) {
            is AppResult.Done -> {
                val user = UserModel(
                    uid = remoteRes.data.uid,
                    name = remoteRes.data.name,
                    lastname = remoteRes.data.lastname,
                    email = email
                )
                userLocalDataSource.saveUser(user)
            }

            is AppResult.Error -> remoteRes
        }

    }

    override suspend fun signup(
        email: String,
        password: String,
        name: String,
        lastname: String
    ): EmptyDataResult<DataError> {
        val remoteRes = userRemoteDataSource.signup(
            email = email,
            name = name,
            password = password,
            lastname = lastname
        )
        return when (remoteRes) {
            is AppResult.Done -> {
                val user = UserModel(
                    uid = remoteRes.data.uid,
                    name = name, lastname = lastname, email = email
                )
                userLocalDataSource.saveUser(user)
            }

            is AppResult.Error -> remoteRes
        }

    }

    override suspend fun update(
        user: UserModel,
    ): EmptyDataResult<DataError> {
        val localRes = userLocalDataSource.updateUser(user)
        return if (localRes is AppResult.Done) {
            userRemoteDataSource.update(user)
        } else {
            localRes
        }
    }


    override fun getUserData(): Flow<UserModel> = userLocalDataSource.getUserData()

}