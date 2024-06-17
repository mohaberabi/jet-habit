package com.mohaberabi.jethabbit.features.auth.presentation.viewmodel

import androidx.annotation.StringRes
import com.mohaberabi.jethabbit.core.domain.model.validator.ValidatorResult

data class AuthState(
    val loading: Boolean = false,
    val email: String = "",
    val name: String = "",
    val lastName: String = "",
    val password: String = "",
    val emailValid: Boolean = false,
    val passwordValid: ValidatorResult = ValidatorResult(),
    val nameValid: ValidatorResult = ValidatorResult(),
    val lastNameValid: ValidatorResult = ValidatorResult(),
    val isLogin: Boolean = true,
) {

    private val canLogin: Boolean
        get() = emailValid && !passwordValid.dirty
    val canAuth: Boolean
        get() = if (isLogin) canLogin else {
            canLogin && !nameValid.dirty && !lastNameValid.dirty
        }
}