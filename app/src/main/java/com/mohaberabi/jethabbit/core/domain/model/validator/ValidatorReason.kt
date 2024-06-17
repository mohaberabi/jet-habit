package com.mohaberabi.jethabbit.core.domain.model.validator

import androidx.annotation.StringRes
import com.mohaberabi.jethabbit.R


data class ValidatorResult(
    val valid: Boolean = false,
    val reason: ValidatorReason? = null
) {
    val dirty: Boolean
        get() = !valid && reason != null
}

sealed interface ValidatorReason {

    @get:StringRes
    val resId: Int

}


enum class EmailValidatorReason(
    @StringRes override val resId: Int,
) : ValidatorReason {
    INCORRECT_EMAIL(
        R.string.wrong_email,
    )
}

enum class SimpleValidatorReason(
    @StringRes override val resId: Int,
) : ValidatorReason {
    BLANK(
        R.string.required,
    ),
}

enum class PasswordValidatorReason(
    @StringRes override val resId: Int,
) : ValidatorReason {

    VERY_SHORT(
        R.string.fatel_error,
    ),

    SPECIAL_CHAR(
        R.string.very_short,
    ),

    UPPER_REQUIRED(
        R.string.needs_upper,
    ),
    LOWER_REQUIRED(
        R.string.needs_lower,
    ),

    NUMBERS_REQUIRED(
        R.string.needs_digit,
    )
}