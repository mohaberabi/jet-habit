package com.mohaberabi.jethabbit.features.settings.presentation.viewmodel

import com.mohaberabi.jethabbit.core.util.UiText

sealed interface SettingsActions {
    data object ToggleDialog : SettingsActions

    data object OnSignOutClick : SettingsActions
    data object OnUpdateClick : SettingsActions
    data class OnNameChanged(val name: String) : SettingsActions
    data class OnLastNameChanged(val lastname: String) : SettingsActions
}

sealed interface SettingsEvents {
    data object SignedOut : SettingsEvents
    data class Error(val error: UiText) : SettingsEvents

}