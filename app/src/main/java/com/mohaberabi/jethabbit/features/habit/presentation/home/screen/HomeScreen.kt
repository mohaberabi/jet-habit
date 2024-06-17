package com.mohaberabi.jethabbit.features.habit.presentation.home.screen

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jethabbit.R
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.presentation.compose.AppError
import com.mohaberabi.jethabbit.core.presentation.compose.AppLoader
import com.mohaberabi.jethabbit.core.presentation.compose.EventCollector
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabbitAlertDialog
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabbitAppBar
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabitFab
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabitScaffold
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing
import com.mohaberabi.jethabbit.core.util.extensions.grantedNotificationPermission
import com.mohaberabi.jethabbit.core.util.extensions.needsNotificationsPermissions
import com.mohaberabi.jethabbit.core.util.extensions.shouldShowNotificationsRationale
import com.mohaberabi.jethabbit.features.habit.presentation.home.compose.DateSelector
import com.mohaberabi.jethabbit.features.habit.presentation.home.compose.HabitCheckBox
import com.mohaberabi.jethabbit.features.habit.presentation.home.viewmodel.HomeActions
import com.mohaberabi.jethabbit.features.habit.presentation.home.viewmodel.HomeEvents
import com.mohaberabi.jethabbit.features.habit.presentation.home.viewmodel.HomeState
import com.mohaberabi.jethabbit.features.habit.presentation.home.viewmodel.HomeViewModel
import java.time.ZonedDateTime


@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    onGoSettings: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onGoDetails: (String?) -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val context = LocalContext.current
    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val selectedDate by homeViewModel.choosedDate.collectAsStateWithLifecycle()

    EventCollector(flow = homeViewModel.event) { event ->
        when (event) {
            is HomeEvents.Error -> onShowSnackBar(event.error.asString(context))
            HomeEvents.HabitUpdated -> onShowSnackBar(context.getString(R.string.habit_completed))
        }
    }
    HomeScreen(
        onGoSettings = onGoSettings,
        modifier = modifier,
        state = state,
        onAction = { action ->
            if (action is HomeActions.OnHabitClick) {
                onGoDetails(action.id)
            } else {
                homeViewModel.onAction(action)
            }
        },
        selectedDate = selectedDate
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onGoSettings: () -> Unit,
    onAction: (HomeActions) -> Unit,
    state: HomeState,
    selectedDate: ZonedDateTime,
) {


    val notificationLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { granted ->
            if (granted) {
                onAction(HomeActions.OnHabitClick(null))
            }
        }

    var showRationaleDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val grantedNotifications by remember(
        context.grantedNotificationPermission(),
    ) {
        mutableStateOf(context.grantedNotificationPermission())
    }

    val shouldShowNotificationsRationale by remember(
        activity.shouldShowNotificationsRationale(),
    ) {
        mutableStateOf(activity.shouldShowNotificationsRationale())
    }

    JetHabitScaffold(
        modifier = modifier,
        topAppBar = {
            JetHabbitAppBar(
                showBackButton = false,
                actions = {
                    JetHabitFab(
                        icon = Icons.Default.Settings,
                        onClick = { onGoSettings() },
                    )
                },
            )
        },
        fab = {
            JetHabitFab(
                icon = Icons.Default.Add,
                onClick = {
                    if (grantedNotifications) {
                        onAction(HomeActions.OnHabitClick(null))
                    } else {
                        if (shouldShowNotificationsRationale) {
                            showRationaleDialog = true
                        } else {
                            if (needsNotificationsPermissions()) {
                                notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        }
                    }
                },
            )
        },

        ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .padding(Spacing.lg)
        ) {
            when (state) {
                HomeState.Error -> AppError()
                HomeState.Loading -> AppLoader()
                is HomeState.Done -> HabitsLoaded(
                    habits = state.habits,
                    onDateSelect = { onAction(HomeActions.ChangeDate(it)) },
                    onHabitClick = { onAction(HomeActions.OnHabitClick(it)) },
                    selectedDate = selectedDate,
                    onComplete = { onAction(HomeActions.OnCompleteHabit(it)) }
                )

            }
        }

    }

    if (showRationaleDialog) {
        JetHabbitAlertDialog(
            title = "Notifications permission",
            subtitle = "We need access to notifications in order to allow us remind you of your habits",
            onDismiss = { showRationaleDialog = false },
            onNegative = { showRationaleDialog = false }, onPositive = {
                showRationaleDialog = false
                notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        )
    }
}

@Composable
private fun HabitsLoaded(
    modifier: Modifier = Modifier,
    habits: List<Habit>,
    onHabitClick: (String) -> Unit,
    onDateSelect: (ZonedDateTime) -> Unit,
    onComplete: (Habit) -> Unit,

    selectedDate: ZonedDateTime,
) {

    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Text(
                text = stringResource(id = R.string.slogan),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black)
            )
        }
        item {
            Spacer(
                modifier = Modifier
                    .height(Spacing.md)
            )

        }
        item {
            DateSelector(
                selectedDate = selectedDate,
                onDateSelect = onDateSelect,
                currentDate = ZonedDateTime.now()
            )
        }
        items(habits) { habit ->
            HabitCheckBox(
                habit = habit,
                isChecked = habit.completedDates.toSet().contains(selectedDate.toLocalDate()),
                onClick = { onHabitClick(habit.id) },
                onCheck = { onComplete(habit) }
            )
        }
    }


}


@Preview
@Composable
private fun PreviewHomeScreen() {
    JetHabbitTheme {
        HomeScreen(
            onGoSettings = {},
            onAction = {},
            state = HomeState.Done(listOf()),
            selectedDate = ZonedDateTime.now()
        )
    }
}