package com.mohaberabi.jethabbit.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.jethabbit.core.util.error.asUiText
import com.mohaberabi.jethabbit.core.domain.model.fold
import com.mohaberabi.jethabbit.core.domain.model.validator.ValidatorResult
import com.mohaberabi.jethabbit.features.auth.domain.usecase.AuthUseCases
import com.mohaberabi.jethabbit.features.auth.domain.usecase.UserValidationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val validators: UserValidationUseCases,
) : ViewModel() {

    private val simpleValidator = validators.simpleValidator
    private val emailValidator = validators.emailValidator
    private val passwordValidator = validators.passwordValidator

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _event = Channel<AuthEvents>()
    val event = _event.receiveAsFlow()
    fun onAction(action: AuthActions) {
        when (action) {
            is AuthActions.EmailChanged -> emailChanged(action.email)
            is AuthActions.LastNameChanged -> lastNameChanged(action.lastName)
            is AuthActions.NameChanged -> nameChanged(action.name)
            is AuthActions.PasswordChanged -> passwordChanged(action.password)
            AuthActions.RequestAuth -> {
                if (_state.value.isLogin) {
                    login()
                } else {
                    signUp()
                }
            }

            AuthActions.ToggleAuthMethod -> toggleAuthMethod()
        }
    }

    private fun nameChanged(name: String) = _state.update {
        it.copy(
            name = name,
            nameValid = simpleValidator(name),
        )
    }

    private fun lastNameChanged(lastName: String) = _state.update {
        it.copy(lastName = lastName, lastNameValid = simpleValidator(lastName))
    }

    private fun emailChanged(email: String) = _state.update {
        it.copy(email = email, emailValid = emailValidator(email))
    }

    private fun toggleAuthMethod() = _state.update {
        it.copy(
            isLogin = !it.isLogin,
            emailValid = false,
            passwordValid = ValidatorResult(),
            nameValid = ValidatorResult(),
            lastNameValid = ValidatorResult(),
            name = "",
            password = "",
            email = "",
            lastName = ""
        )
    }

    private fun passwordChanged(password: String) =
        _state.update { it.copy(password = password, passwordValid = passwordValidator(password)) }

    private fun signUp() {
        _state.update { it.copy(loading = true) }
        val stateVal = _state.value

        viewModelScope.launch {
            val res =
                authUseCases.signUpUseCase(
                    email = stateVal.email,
                    password = stateVal.password,
                    lastName = stateVal.lastName,
                    name = stateVal.name
                )
            res.fold(
                whenDone = { _event.send(AuthEvents.AuthedDone) },
                whenError = { error ->
                    _event.send(AuthEvents.Error(error.asUiText()))
                    _state.update { it.copy(loading = false) }
                },
            )
        }
    }

    private fun login() {
        _state.update { it.copy(loading = true) }
        val stateVal = _state.value

        viewModelScope.launch {
            val res =
                authUseCases.signInUseCase(email = stateVal.email, password = stateVal.password)
            res.fold(
                whenDone = { _event.send(AuthEvents.AuthedDone) },
                whenError = { error ->
                    _event.send(AuthEvents.Error(error.asUiText()))
                    _state.update { it.copy(loading = false) }
                },
            )
        }
    }
}