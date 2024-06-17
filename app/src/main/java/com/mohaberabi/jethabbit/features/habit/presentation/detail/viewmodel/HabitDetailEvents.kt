package com.mohaberabi.jethabbit.features.habit.presentation.detail.viewmodel

import com.mohaberabi.jethabbit.core.util.UiText

sealed interface HabitDetailEvents {


    data class Error(val error: UiText) : HabitDetailEvents
    data object HabitAdded : HabitDetailEvents
    data object HabitDeleted : HabitDetailEvents

}