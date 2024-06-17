package com.mohaberabi.jethabbit.core.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme


@Composable
fun AppLoader(
    modifier: Modifier = Modifier,
) {


    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        CircularProgressIndicator(
            strokeWidth = 5.dp,
            color = Color.Gray,
            trackColor = MaterialTheme.colorScheme.primary
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewAppLoader() {
    JetHabbitTheme {

        AppLoader()
    }
}