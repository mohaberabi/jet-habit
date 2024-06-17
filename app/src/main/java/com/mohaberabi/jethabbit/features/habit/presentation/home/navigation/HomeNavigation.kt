package com.mohaberabi.jethabbit.features.habit.presentation.home.navigation

import androidx.compose.foundation.layout.Column
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jethabbit.features.auth.presentation.navigation.AuthRoute
import com.mohaberabi.jethabbit.features.habit.presentation.detail.navigation.navigateToHabitDetail
import com.mohaberabi.jethabbit.features.habit.presentation.home.screen.HomeScreenRoot
import com.mohaberabi.jethabbit.features.settings.presentation.navigation.navigateToSettingsScreen
import kotlinx.serialization.Serializable


@Serializable
data object HomeRoute


fun NavGraphBuilder.homeScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<HomeRoute> {

    HomeScreenRoot(
        onGoSettings = { navController.navigateToSettingsScreen() },
        onGoDetails = { navController.navigateToHabitDetail(habitId = it) },
        onShowSnackBar = onShowSnackBar,
    )
}


fun NavController.navigateToHomeScreen() = navigate(HomeRoute) {
    popUpTo(AuthRoute) {
        inclusive = true
        saveState = false
    }
    restoreState = false
}