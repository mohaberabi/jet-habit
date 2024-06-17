package com.mohaberabi.jethabbit.features.habit.presentation.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jethabbit.features.habit.presentation.detail.screen.HabitDetailScreenRoot
import kotlinx.serialization.Serializable


@Serializable
data class HabitDetailRoute(val id: String? = null)


fun NavGraphBuilder.habitDetailScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<HabitDetailRoute> {

    HabitDetailScreenRoot(
        onShowSnackBar = onShowSnackBar,
        onGoBack = { navController.popBackStack() },
    )
}


fun NavController.navigateToHabitDetail(
    habitId: String? = null,
) = navigate(HabitDetailRoute(habitId))