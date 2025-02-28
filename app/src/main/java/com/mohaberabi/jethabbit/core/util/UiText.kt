package com.mohaberabi.jethabbit.core.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {


    data object Empty : UiText()
    data class DynamicString(
        val value:
        String
    ) : UiText()


    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText()


    @Composable
    fun asString(): String {
        return when (this) {
            is Empty -> ""
            is DynamicString -> value
            is StringResource -> stringResource(id = id, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is Empty -> ""
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}