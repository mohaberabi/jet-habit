package com.mohaberabi.jethabbit.core.domain.model.validator

import javax.inject.Inject

class SimpleValidator @Inject constructor() {
    operator fun invoke(field: String): ValidatorResult {
        val pure = field.trim()
        var valid = true
        var reason: ValidatorReason? = null
        if (pure.isEmpty()) {
            valid = false
            reason = SimpleValidatorReason.BLANK
        }
        return ValidatorResult(
            valid = valid,
            reason = reason
        )

    }
}