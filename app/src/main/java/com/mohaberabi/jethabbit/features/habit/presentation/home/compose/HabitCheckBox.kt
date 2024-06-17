package com.mohaberabi.jethabbit.features.habit.presentation.home.compose

import android.widget.CheckBox
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jethabbit.core.domain.model.Habit
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing
import java.time.LocalTime
import java.time.ZonedDateTime


@Composable
fun HabitCheckBox(
    modifier: Modifier = Modifier,
    habit: Habit,
    isChecked: Boolean,
    onClick: () -> Unit = {},
    onCheck: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.sm)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = habit.name,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheck,
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewHabitCheckBox() {
    JetHabbitTheme {
        HabitCheckBox(
            habit = Habit(
                name = "Do Kotlin coding until you die",
                completedDates = listOf(),
                frequency = listOf(),
                reminder = LocalTime.now(),
                id = "id"
            ),
            isChecked = true
        )
    }
}