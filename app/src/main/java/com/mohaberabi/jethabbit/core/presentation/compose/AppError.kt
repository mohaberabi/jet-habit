package com.mohaberabi.jethabbit.core.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mohaberabi.jethabbit.R
import com.mohaberabi.jethabbit.core.util.error.AppError


@Composable
fun AppError(
    modifier: Modifier = Modifier,
    error: String = stringResource(id = R.string.unknown_error)
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = error
        )
        Text(
            text = error,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.error)
        )
    }
}