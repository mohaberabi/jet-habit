package com.mohaberabi.jethabbit.core.presentation.compose

import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing

@Composable
fun JetHabbitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    label: String = "",
    loading: Boolean = false,
    labelColor: Color = MaterialTheme.colorScheme.secondary,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    loadingColor: Color = MaterialTheme.colorScheme.secondary,
    disabledColor: Color = Color.DarkGray
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(Spacing.md),
        shape = RoundedCornerShape(Spacing.lg),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) buttonColor else disabledColor,
            disabledContentColor = Color.Gray
        )
    ) {


        Box(
            modifier = Modifier.padding(Spacing.xs),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(if (loading) 1f else 0f),
                strokeWidth = 4.dp,
                color = loadingColor,
            )
            Text(
                modifier = Modifier
                    .alpha(if (!loading) 1f else 0f),
                text = label,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = if (enabled) labelColor else Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                )
            )
        }


    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewJetHabitButton() {


    JetHabbitTheme(

    ) {
        JetHabbitButton(
            label = "Get Started"
        )
    }
}