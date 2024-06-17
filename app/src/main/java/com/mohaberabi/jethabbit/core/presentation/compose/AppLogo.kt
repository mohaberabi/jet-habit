package com.mohaberabi.jethabbit.core.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jethabbit.R
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing


@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .wrapContentHeight()
            .padding(Spacing.sm)
    ) {
        Text(
            text = "J",
            style = MaterialTheme.typography
                .displayLarge
                .copy(fontWeight = FontWeight.Black)
        )

        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )
        Spacer(
            modifier = Modifier
                .height(Spacing.sm)
        )
        Text(
            text = stringResource(R.string.slogan),
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyLarge
        )

    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewAppLogo() {

    JetHabbitTheme {

        AppLogo(

        )
    }

}