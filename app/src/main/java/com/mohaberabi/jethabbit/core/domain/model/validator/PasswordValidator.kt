package com.mohaberabi.jethabbit.core.domain.model.validator

import javax.inject.Inject

class PasswordValidator @Inject constructor() {

    operator fun invoke(password: String): ValidatorResult {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasLower = password.any { it.isLowerCase() }
        val hasUpper = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val reason: PasswordValidatorReason? = when {
            !hasMinLength -> PasswordValidatorReason.VERY_SHORT
            !hasDigit -> PasswordValidatorReason.NUMBERS_REQUIRED
            !hasUpper -> PasswordValidatorReason.UPPER_REQUIRED
            !hasLower -> PasswordValidatorReason.LOWER_REQUIRED
            else -> null
        }
        return ValidatorResult(
            reason = reason,
            valid = reason == null
        )
    }

    companion object {

        const val MIN_PASSWORD_LENGTH = 9
    }
}