package com.mohaberabi.jethabbit.features.settings.domain.usecase

import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel
import com.mohaberabi.jethabbit.features.auth.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(user: UserModel): EmptyDataResult<DataError> =
        userRepository.update(user)
}