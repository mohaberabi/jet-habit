package com.mohaberabi.jethabbit.features.habit.presentation.home.viewmodel

import com.mohaberabi.jethabbit.core.domain.model.Habit

sealed interface HomeState {


    data object Loading : HomeState
    data object Error : HomeState
    data class Done(val habits: List<Habit>) : HomeState
}