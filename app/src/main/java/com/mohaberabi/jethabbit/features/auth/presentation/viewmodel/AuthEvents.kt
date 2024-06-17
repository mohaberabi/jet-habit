package com.mohaberabi.jethabbit.features.auth.presentation.viewmodel

import com.mohaberabi.jethabbit.core.util.UiText

sealed interface AuthEvents {


    data class Error(val error: UiText) : AuthEvents
    data object AuthedDone : AuthEvents
}