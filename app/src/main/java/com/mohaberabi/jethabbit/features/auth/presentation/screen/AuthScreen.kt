package com.mohaberabi.jethabbit.features.auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jethabbit.R
import com.mohaberabi.jethabbit.core.presentation.compose.AppLogo
import com.mohaberabi.jethabbit.core.presentation.compose.AppTextField
import com.mohaberabi.jethabbit.core.presentation.compose.EventCollector
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabbitAppBar
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabbitButton
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabitScaffold
import com.mohaberabi.jethabbit.core.presentation.compose.PasswordTextField
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing
import com.mohaberabi.jethabbit.features.auth.presentation.viewmodel.AuthActions
import com.mohaberabi.jethabbit.features.auth.presentation.viewmodel.AuthEvents
import com.mohaberabi.jethabbit.features.auth.presentation.viewmodel.AuthState
import com.mohaberabi.jethabbit.features.auth.presentation.viewmodel.AuthViewModel


@Composable
fun AuthScreenRoot(
    modifier: Modifier = Modifier,
    onAuthed: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    EventCollector(
        flow = viewModel.event,
    ) { event ->
        when (event) {
            AuthEvents.AuthedDone -> onAuthed()
            is AuthEvents.Error -> onShowSnackBar(event.error.asString(context))
        }
    }
    AuthScreen(
        state = state,
        modifier = modifier,
        onAction = viewModel::onAction,
    )

}

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onAction: (AuthActions) -> Unit,
) {

    val slogan =
        if (state.isLogin) R.string.login_Slogan else R.string.signup_slogan

    val authActionQuestion =
        if (state.isLogin) R.string.signup_question else R.string.login_question


    val authAction =

        if (state.isLogin) R.string.create_account else R.string.login

    JetHabitScaffold(
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.lg),
            verticalArrangement = Arrangement.Center,
        ) {
            AppLogo()
            Spacer(modifier = Modifier.height(Spacing.md))
            Text(
                text = stringResource(slogan),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.height(Spacing.md))
            AppTextField(
                error = !state.emailValid && state.email.isNotEmpty(),
                label = stringResource(R.string.e_mail),
                placeHolder = stringResource(R.string.email_placeholder),
                value = state.email,
                errorText = if (state.emailValid) null else stringResource(id = R.string.wrong_email),

                onChanged = {
                    onAction(AuthActions.EmailChanged(it))
                }
            )


            if (!state.isLogin) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {

                    AppTextField(
                        label = stringResource(R.string.first_name),
                        placeHolder = stringResource(R.string.jet),
                        value = state.name,

                        errorText = state.nameValid.reason?.let {
                            stringResource(id = it.resId)
                        },
                        widthFlex = 0.5f,
                        error = state.nameValid.dirty,

                        onChanged = {
                            onAction(AuthActions.NameChanged(it))

                        }
                    )
                    AppTextField(
                        label = stringResource(R.string.last_name),
                        placeHolder = stringResource(R.string.habit),
                        value = state.lastName,
                        widthFlex = 1f,

                        error = state.lastNameValid.dirty,
                        errorText = state.nameValid.reason?.let {
                            stringResource(id = it.resId)
                        },
                        onChanged = {
                            onAction(AuthActions.LastNameChanged(it))

                        }
                    )
                }
            }
            PasswordTextField(
                value = state.password,
                error = state.passwordValid.dirty,
                errorText = state.passwordValid.reason?.let {
                    stringResource(id = it.resId)
                },

                onChange = {
                    onAction(AuthActions.PasswordChanged(it))
                },
            )
            Spacer(modifier = Modifier.height(Spacing.md))

            JetHabbitButton(
                label = stringResource(R.string.create_account),
                onClick = {
                    onAction(AuthActions.RequestAuth)
                },
                loading = state.loading,
                enabled = state.canAuth
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = stringResource(id = authActionQuestion))
                TextButton(onClick = { onAction(AuthActions.ToggleAuthMethod) }) {
                    Text(
                        text = stringResource(id = authAction),
                    )
                }
            }

        }


    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewAuthScreen() {
    JetHabbitTheme {
        AuthScreen(
            state = AuthState(),
            onAction = {},
        )
    }
}