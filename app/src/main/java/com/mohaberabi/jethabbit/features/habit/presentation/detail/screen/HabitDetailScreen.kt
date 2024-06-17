package com.mohaberabi.jethabbit.features.habit.presentation.detail.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jethabbit.R
import com.mohaberabi.jethabbit.core.presentation.compose.AppLoader
import com.mohaberabi.jethabbit.core.presentation.compose.AppTextField
import com.mohaberabi.jethabbit.core.presentation.compose.AppTimePicker
import com.mohaberabi.jethabbit.core.presentation.compose.EventCollector
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabbitAlertDialog
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabbitAppBar
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabitFab
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabitScaffold
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing
import com.mohaberabi.jethabbit.features.habit.presentation.detail.compose.DateFrequencyChoicer
import com.mohaberabi.jethabbit.features.habit.presentation.detail.navigation.HabitDetailRoute
import com.mohaberabi.jethabbit.features.habit.presentation.detail.viewmodel.HabitDetailActions
import com.mohaberabi.jethabbit.features.habit.presentation.detail.viewmodel.HabitDetailEvents
import com.mohaberabi.jethabbit.features.habit.presentation.detail.viewmodel.HabitDetailViewModel
import com.mohaberabi.jethabbit.features.habit.presentation.detail.viewmodel.HabitDetailsState


@Composable
fun HabitDetailScreenRoot(
    modifier: Modifier = Modifier,
    onShowSnackBar: (String) -> Unit,
    onGoBack: () -> Unit,
    viewModel: HabitDetailViewModel = hiltViewModel(),

    ) {

    val context = LocalContext.current
    EventCollector(flow = viewModel.event) { event ->
        when (event) {
            is HabitDetailEvents.Error -> onShowSnackBar(event.error.asString(context))
            HabitDetailEvents.HabitAdded,
            HabitDetailEvents.HabitDeleted
            -> onGoBack()
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    HabitDetailScreen(
        onAction = viewModel::onAction,
        state = state,
        modifier = modifier,
        onGoBack = onGoBack,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(
    modifier: Modifier = Modifier,
    onAction: (HabitDetailActions) -> Unit,
    state: HabitDetailsState,
    onGoBack: () -> Unit
) {
    JetHabitScaffold(
        modifier = modifier,
        fab = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.sm),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (state.canAdd)
                        JetHabitFab(
                            icon = Icons.Default.Done,
                            onClick = { onAction(HabitDetailActions.AddHabit) },
                        )
                    Spacer(modifier = Modifier.height(Spacing.sm))

                    if (state.showDeleteButton)
                        JetHabitFab(
                            icon = Icons.Default.Delete,
                            onClick = { onAction(HabitDetailActions.ToggleDeleteDialog) },
                        )
                }

            }


        },
        topAppBar = {
            JetHabbitAppBar(
                showBackButton = true,
                onBackClick = onGoBack,
                title = "Add Habit"
            )
        }
    ) { padding ->

        if (state.loading) {
            AppLoader(modifier = modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(Spacing.lg),
            ) {

                AppTextField(
                    label = stringResource(R.string.name),
                    value = state.habitName,
                    onChanged = { onAction(HabitDetailActions.NameChanged(it)) },
                )

                Spacer(modifier = Modifier.height(Spacing.lg))
                Text(
                    text = stringResource(R.string.select_frequency),
                    style = MaterialTheme.typography.titleLarge
                        .copy(fontWeight = FontWeight.Bold),
                )

                DateFrequencyChoicer(
                    onDaySelect = { onAction(HabitDetailActions.FrequencyChanged(it)) },
                    selectedDays = state.frequencies.toList()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAction(HabitDetailActions.ToggleTimePicker) }
                ) {
                    Text(
                        text = stringResource(R.string.remind_at),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = state.habitReminder.formatted
                    )
                }

            }
        }

    }
    if (state.showTimePicker) {
        AppTimePicker(
            onTimeChanged = { onAction(HabitDetailActions.TimeChanged(it)) },
            onDismiss = { onAction(HabitDetailActions.ToggleTimePicker) }
        )

    }
    if (state.showDeleteDialog) {
        JetHabbitAlertDialog(
            title = stringResource(R.string.delete_habit),
            subtitle = stringResource(R.string.delete_habit_subttl),
            onPositive = { onAction(HabitDetailActions.DeleteHabit) },
            onNegative = { onAction(HabitDetailActions.ToggleDeleteDialog) },
            onDismiss = { onAction(HabitDetailActions.ToggleDeleteDialog) },
            positiveText = stringResource(R.string.delete),
            isEmergent = true,
            negativeText = stringResource(R.string.changed_my_mind)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAddHabitScreen() {


    JetHabbitTheme {

        HabitDetailScreen(
            state = HabitDetailsState(),
            onGoBack = {},
            onAction = {}
        )
    }
}