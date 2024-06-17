package com.mohaberabi.jethabbit.features.auth.domain.usecase

import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
)