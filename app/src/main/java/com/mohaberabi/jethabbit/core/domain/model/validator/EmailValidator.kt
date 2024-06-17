package com.mohaberabi.jethabbit.core.domain.model.validator

import android.util.Patterns
import javax.inject.Inject

interface EmailValidator {
    operator fun invoke(email: String): Boolean
}


class DefaultEmailValidator @Inject constructor(
) : EmailValidator {
    override operator fun invoke(
        email: String,
    ): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}