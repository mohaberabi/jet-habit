package com.mohaberabi.jethabbit.features.settings.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jethabbit.features.auth.presentation.navigation.AuthRoute
import com.mohaberabi.jethabbit.features.settings.presentation.screen.SettingsScreenRoot
import kotlinx.serialization.Serializable


@Serializable
data object SettingsRoute


fun NavGraphBuilder.settingsScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<SettingsRoute> {
    SettingsScreenRoot(
        onShowSnackBar = onShowSnackBar,
        onSignOut = {
            navController.navigate(AuthRoute) {
                popUpTo(AuthRoute) {
                    inclusive = false
                    saveState = false
                }
                restoreState = false
            }
        },
        onGoBack = { navController.popBackStack() },
    )
}

fun NavController.navigateToSettingsScreen() = navigate(SettingsRoute)