package com.mohaberabi.jethabbit.features.auth.presentation.viewmodel

import com.google.rpc.context.AttributeContext.Auth

sealed interface AuthActions {

    data object ToggleAuthMethod : AuthActions

    data class NameChanged(val name: String) : AuthActions
    data class LastNameChanged(val lastName: String) : AuthActions
    data class EmailChanged(val email: String) : AuthActions
    data class PasswordChanged(val password: String) : AuthActions
    data object RequestAuth : AuthActions
}