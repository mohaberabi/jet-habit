package com.mohaberabi.jethabbit.features.settings.presentation.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jethabbit.R
import com.mohaberabi.jethabbit.core.presentation.compose.AppTextField
import com.mohaberabi.jethabbit.core.presentation.compose.EventCollector
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabbitAlertDialog
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabbitAppBar
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabbitButton
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabitScaffold
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing
import com.mohaberabi.jethabbit.features.settings.presentation.viewmodel.SettingsActions
import com.mohaberabi.jethabbit.features.settings.presentation.viewmodel.SettingsEvents
import com.mohaberabi.jethabbit.features.settings.presentation.viewmodel.SettingsState
import com.mohaberabi.jethabbit.features.settings.presentation.viewmodel.SettingsViewModel


@Composable
fun SettingsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onShowSnackBar: (String) -> Unit,
    onSignOut: () -> Unit,
    onGoBack: () -> Unit,
) {


    val context = LocalContext.current
    EventCollector(flow = viewModel.event) { event ->
        when (event) {
            is SettingsEvents.Error -> onShowSnackBar(event.error.asString(context))
            SettingsEvents.SignedOut -> onSignOut()
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsScreen(
        modifier = modifier,
        onAction = viewModel::onAction,
        onGoBack = onGoBack,
        state = state,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onAction: (SettingsActions) -> Unit,
    onGoBack: () -> Unit,
    state: SettingsState,
) {


    JetHabitScaffold(
        modifier = modifier,
        topAppBar = {
            JetHabbitAppBar(
                showBackButton = true,
                onBackClick = onGoBack,
            )
        }
    ) {

            padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(Spacing.lg)
                .scrollable(rememberScrollState(), Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AppTextField(
                label = stringResource(id = R.string.e_mail),
                value = state.user.email,
                isReadOnly = true,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppTextField(
                    widthFlex = 0.5f,
                    label = stringResource(id = R.string.first_name),
                    value = state.user.lastname,
                    onChanged = { onAction(SettingsActions.OnNameChanged(it)) }

                )
                AppTextField(
                    widthFlex = 1f,
                    value = state.user.lastname,
                    onChanged = { onAction(SettingsActions.OnLastNameChanged(it)) },
                    label = stringResource(id = R.string.last_name)
                )
            }



            JetHabbitButton(
                label = stringResource(R.string.save),
                loading = state.loading,
                enabled = true,
                onClick = {
                    onAction(SettingsActions.OnUpdateClick)
                }
            )
            TextButton(
                onClick = {
                    onAction(SettingsActions.ToggleDialog)
                },
            ) {
                Text(text = stringResource(R.string.sign_out))
            }


        }

    }

    if (state.showSignOutDialog) {
        JetHabbitAlertDialog(
            title = stringResource(id = R.string.sign_out),
            subtitle = stringResource(R.string.sign_out_que),
            positiveText = stringResource(id = R.string.sign_out),
            negativeText = stringResource(id = R.string.go_back),
            onPositive = { onAction(SettingsActions.OnSignOutClick) },
            onNegative = {
                onAction(SettingsActions.ToggleDialog)
            },
            isEmergent = true,
            onDismiss = {
                onAction(SettingsActions.ToggleDialog)
            }
        )
    }

}

@Preview
@Composable
private fun PreviewSettings() {

    JetHabbitTheme {
        SettingsScreen(
            onAction = {},
            onGoBack = {},
            state = SettingsState()
        )
    }
}