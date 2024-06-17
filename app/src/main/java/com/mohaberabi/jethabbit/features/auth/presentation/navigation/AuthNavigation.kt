package com.mohaberabi.jethabbit.features.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.rpc.context.AttributeContext.Auth
import com.mohaberabi.jethabbit.features.auth.presentation.screen.AuthScreenRoot
import com.mohaberabi.jethabbit.features.habit.presentation.home.navigation.HomeRoute
import com.mohaberabi.jethabbit.features.habit.presentation.home.navigation.navigateToHomeScreen
import kotlinx.serialization.Serializable


@Serializable
data object AuthRoute


fun NavGraphBuilder.authScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit
) = composable<AuthRoute> {
    AuthScreenRoot(
        onAuthed = {
            navController.navigateToHomeScreen()
        },
        onShowSnackBar = onShowSnackBar
    )
}


