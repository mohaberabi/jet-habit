package com.mohaberabi.jethabbit.features.settings.presentation.viewmodel

import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel

data class SettingsState(
    val showSignOutDialog: Boolean = false,
    val user: UserModel = UserModel.empty,
    val loading: Boolean = false
)
