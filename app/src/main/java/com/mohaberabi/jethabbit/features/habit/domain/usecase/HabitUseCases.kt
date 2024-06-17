package com.mohaberabi.jethabbit.features.habit.domain.usecase

import javax.inject.Inject

data class HabitUseCases @Inject constructor(
    val getHabitByIdUseCase: GetHabitByIdUseCase,
    val getHabitsByDayOfWeekUseCase: GetHabitsByDayOfWeekUseCase,
    val addHabitUseCase: AddHabitUseCase,
    val deleteHabitUseCase: DeleteHabitUseCase,
    val syncHabitsUseCase: SyncHabitsUseCase,
)
