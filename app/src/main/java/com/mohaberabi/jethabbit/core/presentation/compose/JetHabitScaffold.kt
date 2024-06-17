package com.mohaberabi.jethabbit.core.presentation.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun JetHabitScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    bottomAppBar: @Composable () -> Unit = {},
    fab: @Composable () -> Unit = {},
    fabPosition: FabPosition = FabPosition.End,
    snackBarHostState: SnackbarHostState? = null,
    content: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        bottomBar = bottomAppBar,
        floatingActionButtonPosition = fabPosition,
        snackbarHost = {
            if (snackBarHostState != null)
                SnackbarHost(
                    hostState = snackBarHostState,
                    modifier = Modifier.padding(bottom = 60.dp)
                )
        },
        modifier = modifier,
        topBar = topAppBar,
        floatingActionButton = fab,
    ) {

            padding ->
        content(padding)

    }
}