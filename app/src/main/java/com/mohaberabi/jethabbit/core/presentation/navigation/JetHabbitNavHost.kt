package com.mohaberabi.jethabbit.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mohaberabi.jethabbit.features.auth.presentation.navigation.AuthRoute
import com.mohaberabi.jethabbit.features.auth.presentation.navigation.authScreen
import com.mohaberabi.jethabbit.features.habit.presentation.detail.navigation.habitDetailScreen
import com.mohaberabi.jethabbit.features.habit.presentation.home.navigation.homeScreen
import com.mohaberabi.jethabbit.features.settings.presentation.navigation.settingsScreen


@Composable
fun JetHabbitNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    start: Any = AuthRoute,
    onShowSnackBar: (String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = start,
        modifier = modifier,
    ) {

        homeScreen(
            navController = navHostController,
            onShowSnackBar = onShowSnackBar
        )
        authScreen(
            navController = navHostController,
            onShowSnackBar = onShowSnackBar
        )
        settingsScreen(
            navController = navHostController,
            onShowSnackBar = onShowSnackBar,
        )
        habitDetailScreen(
            navController = navHostController,
            onShowSnackBar = onShowSnackBar,
        )

    }
}
