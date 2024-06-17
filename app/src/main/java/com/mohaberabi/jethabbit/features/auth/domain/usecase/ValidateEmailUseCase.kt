package com.mohaberabi.jethabbit.features.auth.domain.usecase

import com.mohaberabi.jethabbit.core.domain.model.validator.EmailValidator
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val emailValidator: EmailValidator,
) {
    operator fun invoke(
        email: String,
    ): Boolean = emailValidator(email)
}