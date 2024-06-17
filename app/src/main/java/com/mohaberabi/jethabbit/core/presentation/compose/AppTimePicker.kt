package com.mohaberabi.jethabbit.core.presentation.compose


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing
import com.mohaberabi.jethabbit.core.util.extensions.asClock

data class TimePickerResult(
    val hour: Int = 12,
    val minute: Int = 0,
    val is24: Boolean = false
) {

    val formatted: String = "${hour.asClock()}:${minute.asClock()}"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePicker(
    modifier: Modifier = Modifier,
    onTimeChanged: (TimePickerResult) -> Unit,
    onDismiss: () -> Unit = {}
) {

    val state = rememberTimePickerState()



    Dialog(onDismissRequest = onDismiss) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.background(MaterialTheme.colorScheme.background)
        ) {
            TimePicker(
                state = state,
                modifier = Modifier.padding(12.dp),
                colors = TimePickerDefaults.colors(
                    clockDialColor = MaterialTheme.colorScheme.primary,
                    clockDialSelectedContentColor = MaterialTheme.colorScheme.primary,
                    selectorColor = MaterialTheme.colorScheme.onPrimary,
                    clockDialUnselectedContentColor = MaterialTheme.colorScheme.secondary,
                    periodSelectorBorderColor = MaterialTheme.colorScheme.secondary,

                    containerColor = MaterialTheme.colorScheme.primary,
                    timeSelectorSelectedContentColor = MaterialTheme.colorScheme.primary,
                    timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.secondary,
                )
            )
            TextButton(
                onClick = onDismiss,
            ) {
                Text(
                    text = "Save",
                )
            }
        }
    }


    LaunchedEffect(state.hour, state.minute, state.is24hour) {
        onTimeChanged(
            TimePickerResult(
                hour = state.hour,
                minute = state.minute,
                is24 = state.is24hour
            )
        )
    }


}

@Preview(showBackground = true)
@Composable
private fun PreviewAppTimePicker() {

    JetHabbitTheme {

        AppTimePicker(
            onTimeChanged = {},
        )
    }
}