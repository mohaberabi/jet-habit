package com.mohaberabi.jethabbit.features.habit.presentation.detail.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.type.DayOfWeek
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.Spacing


@Composable
fun DateFrequencyItem(
    modifier: Modifier = Modifier,
    date: DayOfWeek,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
) {


    Column(
        modifier = modifier
            .padding(
                vertical = Spacing.sm,
                horizontal = Spacing.xs
            )
    ) {

        Text(text = date.name.take(3))

    }

}