package com.mohaberabi.jethabbit.jethabbit.activity.domain.usecase

import com.mohaberabi.jethabbit.features.auth.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): Boolean {
        val user = userRepository.getUserData().first()
        return user.uid.isNotEmpty()
    }
}