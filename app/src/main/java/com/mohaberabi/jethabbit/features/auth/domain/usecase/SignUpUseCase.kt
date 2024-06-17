package com.mohaberabi.jethabbit.features.auth.domain.usecase

import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.auth.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        lastName: String,
    ): EmptyDataResult<DataError> =
        userRepository.signup(
            name = name,
            password = password,
            lastname = lastName,
            email = email
        )
}