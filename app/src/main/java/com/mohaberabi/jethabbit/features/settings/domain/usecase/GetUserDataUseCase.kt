package com.mohaberabi.jethabbit.features.settings.domain.usecase

import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel
import com.mohaberabi.jethabbit.features.auth.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    operator fun invoke(): Flow<UserModel> = userRepository.getUserData()
}