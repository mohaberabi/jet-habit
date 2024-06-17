package com.mohaberabi.jethabbit.features.habit.presentation.home.viewmodel

import com.mohaberabi.jethabbit.core.util.UiText

sealed interface HomeEvents {


    data class Error(val error: UiText) : HomeEvents
    data object HabitUpdated : HomeEvents
}