package com.mohaberabi.jethabbit.core.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing
import com.mohaberabi.jethabbit.core.util.UiText

@Composable
fun JetHabbitError(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
    errorTitle: UiText = UiText.DynamicString(""),
    errorSubtitle: String = "",
    loading: Boolean = false

) {
    val title = errorTitle.asString()
    JetHabbitErorrBody(
        modifier = modifier,
        onRetry = onRetry,
        title = title,
        subtitle = errorSubtitle,
        loading = loading
    )

}

@Composable
private fun JetHabbitErorrBody(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    onRetry: () -> Unit = {},
    buttonLabel: String = "Try again",
    loading: Boolean = false
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.md),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(
            modifier = Modifier
                .height(Spacing.sm),
        )
        Text(
            text = subtitle,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
        )
        Spacer(
            modifier = Modifier
                .height(Spacing.sm),
        )
        JetHabbitButton(
            label = buttonLabel,
            onClick = onRetry,
            loading = loading,
            loadingColor = MaterialTheme.colorScheme.onPrimary,
            buttonColor = MaterialTheme.colorScheme.error,
            labelColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreivewJetHabbitError() {

    JetHabbitTheme {
        JetHabbitError(
            errorTitle = UiText.DynamicString("\"Something Went Wrong ...\","),
            errorSubtitle = "it might be an error in your network connection please try again"
        )
    }
}
