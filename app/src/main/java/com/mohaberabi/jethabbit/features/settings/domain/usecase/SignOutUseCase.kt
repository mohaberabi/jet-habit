package com.mohaberabi.jethabbit.features.settings.domain.usecase

import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {


    suspend operator fun invoke(): EmptyDataResult<DataError> = settingsRepository.signOut()

}