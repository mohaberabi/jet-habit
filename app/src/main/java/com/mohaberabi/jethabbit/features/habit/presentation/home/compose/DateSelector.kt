package com.mohaberabi.jethabbit.features.habit.presentation.home.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import java.time.ZonedDateTime


@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    selectedDate: ZonedDateTime,
    onDateSelect: (ZonedDateTime) -> Unit,
    size: Int = 4,
    currentDate: ZonedDateTime,

    ) {


    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {


        for (i in size downTo 0) {
            val date = currentDate.minusDays(i.toLong())
            DateItem(
                date = date,
                isSelected = date.toLocalDate() == selectedDate.toLocalDate(),
                onClick = {
                    onDateSelect(date)
                }
            )
        }
    }
}


@Preview
@Composable
private fun DateSelectorPreview() {


    JetHabbitTheme {


        DateSelector(
            selectedDate = ZonedDateTime.now(),
            onDateSelect = {

            },
            currentDate = ZonedDateTime.now()
        )
    }
}