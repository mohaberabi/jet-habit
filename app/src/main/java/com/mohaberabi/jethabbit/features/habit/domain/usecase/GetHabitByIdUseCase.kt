package com.mohaberabi.jethabbit.features.habit.domain.usecase

import com.mohaberabi.jethabbit.features.habit.domain.repository.HabitRepository
import javax.inject.Inject

class GetHabitByIdUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
) {

    suspend operator fun invoke(id: String) = habitRepository.getHabitById(id)
}