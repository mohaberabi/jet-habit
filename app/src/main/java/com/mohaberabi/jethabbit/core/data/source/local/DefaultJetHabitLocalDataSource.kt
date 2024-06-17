package com.mohaberabi.jethabbit.core.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.mohaberabi.jethabbit.core.domain.source.JetHabitLocalDataSource
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultJetHabitLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dispatchers: DispatchersProvider,
) : JetHabitLocalDataSource {
    override suspend fun clear() {
        withContext(dispatchers.io) {
            dataStore.edit { prefs -> prefs.clear() }
        }
    }
}