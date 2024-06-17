package com.mohaberabi.jethabbit.features.habit.presentation.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing
import java.time.DayOfWeek
import java.time.ZonedDateTime

@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    day: DayOfWeek,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    val background =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background

    val textColor =
        if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .size(52.dp),
        contentAlignment = Alignment.Center,

        ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(Spacing.md))
                .size(50.dp)
                .clickable { onClick() }
                .background(background),
            contentAlignment = Alignment.Center,
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = day.name.take(3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = textColor
                )
            }
        }
    }

}

@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    date: ZonedDateTime,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    val background =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background

    val textColor =
        if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .size(52.dp),
        contentAlignment = Alignment.Center,

        ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(Spacing.md))
                .size(50.dp)
                .clickable { onClick() }
                .background(background),
            contentAlignment = Alignment.Center,
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = date.dayOfWeek.name.take(3),
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = date.dayOfMonth.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = textColor
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewDateItem() {


    JetHabbitTheme {

        DateItem(
            date = ZonedDateTime.now(),
            isSelected = true
        ) {

        }
    }
}