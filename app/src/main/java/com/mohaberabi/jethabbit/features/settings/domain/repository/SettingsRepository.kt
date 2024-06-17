package com.mohaberabi.jethabbit.features.settings.domain.repository

import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.error.DataError

interface SettingsRepository {


    suspend fun signOut(): EmptyDataResult<DataError>
}