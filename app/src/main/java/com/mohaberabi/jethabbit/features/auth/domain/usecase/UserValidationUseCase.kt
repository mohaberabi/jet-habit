package com.mohaberabi.jethabbit.features.auth.domain.usecase

import com.mohaberabi.jethabbit.core.domain.model.validator.EmailValidator
import com.mohaberabi.jethabbit.core.domain.model.validator.PasswordValidator
import com.mohaberabi.jethabbit.core.domain.model.validator.SimpleValidator
import javax.inject.Inject

data class UserValidationUseCases @Inject constructor(
    val simpleValidator: SimpleValidator,
    val emailValidator: EmailValidator,
    val passwordValidator: PasswordValidator
)
