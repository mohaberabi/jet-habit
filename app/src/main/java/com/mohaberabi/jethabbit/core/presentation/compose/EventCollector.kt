package com.mohaberabi.jethabbit.core.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.mohaberabi.jethabbit.core.util.DefaultDispatcherProvider
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


@Composable
fun <T> EventCollector(
    key1: Any? = null, key2: Any? = null,
    flow: Flow<T>,
    onEvent: (T) -> Unit,
) {

    val lifeCycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(

        key1 = key1, key2 = key2,
        flow
    ) {

        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }

    }
}