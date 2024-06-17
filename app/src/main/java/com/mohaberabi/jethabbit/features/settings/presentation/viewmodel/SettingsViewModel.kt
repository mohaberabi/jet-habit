package com.mohaberabi.jethabbit.features.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.util.error.asUiText
import com.mohaberabi.jethabbit.features.settings.domain.repository.SettingsRepository
import com.mohaberabi.jethabbit.features.settings.domain.usecase.GetUserDataUseCase
import com.mohaberabi.jethabbit.features.settings.domain.usecase.SignOutUseCase
import com.mohaberabi.jethabbit.features.settings.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserUseCase: GetUserDataUseCase,
    private val updateUserCase: UpdateUserUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()
    private val _event = Channel<SettingsEvents>()

    val event = _event.receiveAsFlow()


    init {
        getUserUseCase().map { user ->
            _state.update { it.copy(user = user) }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: SettingsActions) {
        when (action) {
            SettingsActions.OnSignOutClick -> signOut()
            is SettingsActions.OnLastNameChanged -> lastNameChanged(action.lastname)
            is SettingsActions.OnNameChanged -> nameChanged(action.name)
            SettingsActions.OnUpdateClick -> updateUser()
            SettingsActions.ToggleDialog -> toggleDialog()
        }
    }


    private fun toggleDialog() =
        _state.update { it.copy(showSignOutDialog = !it.showSignOutDialog) }

    private fun lastNameChanged(lastName: String) =
        _state.update { it.copy(user = it.user.copy(lastname = lastName)) }


    private fun nameChanged(name: String) =
        _state.update { it.copy(user = it.user.copy(name = name)) }

    private fun updateUser() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            when (val res = updateUserCase(_state.value.user)) {
                is AppResult.Done -> _state.update { it.copy(loading = false) }
                is AppResult.Error -> {
                    _event.send(SettingsEvents.Error(res.error.asUiText()))
                    _state.update { it.copy(loading = false) }
                }
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            when (val res = signOutUseCase()) {
                is AppResult.Done -> _event.send(SettingsEvents.SignedOut)
                is AppResult.Error -> _event.send(SettingsEvents.Error(res.error.asUiText()))
            }
        }
    }
}