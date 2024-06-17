package com.mohaberabi.jethabbit.features.habit.presentation.detail.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing
import com.mohaberabi.jethabbit.features.habit.presentation.home.compose.DateItem
import java.time.DayOfWeek


@Composable
fun DateFrequencyChoicer(
    modifier: Modifier = Modifier,
    selectedDays: List<DayOfWeek> = listOf(),
    onDaySelect: (DayOfWeek) -> Unit,

    ) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(Spacing.sm)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {


            val days = DayOfWeek.entries.toTypedArray()
            days.forEachIndexed { idnex, day ->
                DateItem(
                    day = day,
                    isSelected = selectedDays.contains(day),
                    onClick = { onDaySelect(day) },
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun DateFrequencyChoicerPrview() {
    JetHabbitTheme {

        DateFrequencyChoicer {

        }

    }
}