package com.mohaberabi.jethabbit.jethabbit.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

data class JetHabitState(


    val scope: CoroutineScope,
    val jetHabitNavController: NavHostController,
    val snackbarHostState: SnackbarHostState
)


@Composable
fun rememberJetHabitState(): JetHabitState {

    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val hostState = remember {
        SnackbarHostState()
    }
    return JetHabitState(
        scope = scope,
        jetHabitNavController = navController,
        snackbarHostState = hostState
    )
}
