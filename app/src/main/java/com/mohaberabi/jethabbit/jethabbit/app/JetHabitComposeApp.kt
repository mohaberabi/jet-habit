package com.mohaberabi.jethabbit.jethabbit.app

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mohaberabi.jethabbit.core.presentation.compose.JetHabitScaffold
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.navigation.JetHabbitNavHost
import com.mohaberabi.jethabbit.features.auth.presentation.navigation.AuthRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Composable
fun JetHabitComposeApp(
    modifier: Modifier = Modifier,
    jethabitState: JetHabitState,
    startRoute: Any = AuthRoute,
) {

    var job: Job? = remember {
        null
    }
    val scope = jethabitState.scope
    val navController = jethabitState.jetHabitNavController
    val hostState = jethabitState.snackbarHostState
    JetHabbitTheme {
        JetHabitScaffold(
            snackBarHostState = hostState,
        ) { padding ->
            JetHabbitNavHost(
                start = startRoute,
                modifier = modifier.padding(padding),
                navHostController = navController,
                onShowSnackBar = {
                    job?.cancel()
                    job = scope.launch {
                        hostState.showSnackbar(it)
                    }
                },
            )

        }

    }

}