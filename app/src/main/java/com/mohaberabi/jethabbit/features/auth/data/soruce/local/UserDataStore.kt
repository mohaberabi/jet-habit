package com.mohaberabi.jethabbit.features.auth.data.soruce.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mohaberabi.jethabbit.core.domain.model.AppResult
import com.mohaberabi.jethabbit.core.util.DispatchersProvider
import com.mohaberabi.jethabbit.core.domain.model.EmptyDataResult
import com.mohaberabi.jethabbit.core.util.error.DataError
import com.mohaberabi.jethabbit.features.auth.domain.model.UserModel
import com.mohaberabi.jethabbit.features.auth.domain.source.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val datastore: DataStore<Preferences>,
    private val dispatchers: DispatchersProvider
) : UserLocalDataSource {
    companion object {
        private val UID_KEY = stringPreferencesKey("uidKey")
        private val NAME_KEY = stringPreferencesKey("nameKey")
        private val LASTNAME_KEY = stringPreferencesKey("lastNameKey")
        private val EMAIL_KEY = stringPreferencesKey("emailKey")

    }

    override suspend fun saveUser(
        user: UserModel,
    ): EmptyDataResult<DataError> {
        return withContext(dispatchers.io) {
            try {
                datastore.edit { prefs ->
                    prefs[NAME_KEY] = user.name
                    prefs[LASTNAME_KEY] = user.lastname
                    prefs[EMAIL_KEY] = user.email
                    prefs[UID_KEY] = user.uid
                }
                AppResult.Done(Unit)
            } catch (e: IOException) {
                e.printStackTrace()
                AppResult.Error(DataError.Local.IOException)
            } catch (e: Exception) {
                e.printStackTrace()
                AppResult.Error(DataError.Local.UNKNOWN)

            }
        }
    }

    override suspend fun clearUser() {
        withContext(dispatchers.io) {
            try {
                datastore.edit { prefs ->
                    prefs.remove(NAME_KEY)
                    prefs.remove(LASTNAME_KEY)
                    prefs.remove(EMAIL_KEY)
                    prefs.remove(UID_KEY)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override suspend fun updateUser(user: UserModel): EmptyDataResult<DataError> {
        return withContext(dispatchers.io) {
            try {
                datastore.edit { prefs ->
                    prefs[NAME_KEY] = user.name
                    prefs[LASTNAME_KEY] = user.lastname
                    prefs[EMAIL_KEY] = user.email
                    prefs[UID_KEY] = user.uid
                }
                AppResult.Done(Unit)
            } catch (e: IOException) {
                e.printStackTrace()
                AppResult.Error(DataError.Local.IOException)
            } catch (e: Exception) {
                e.printStackTrace()
                AppResult.Error(DataError.Local.UNKNOWN)

            }
        }
    }

    override fun getUserData(): Flow<UserModel> {
        return datastore.data.map { prefs ->
            val uid = prefs[UID_KEY] ?: ""
            val name = prefs[NAME_KEY] ?: ""
            val lastname = prefs[LASTNAME_KEY] ?: ""
            val email = prefs[EMAIL_KEY] ?: ""
            UserModel(uid = uid, name = name, lastname = lastname, email = email)
        }.flowOn(dispatchers.io)
    }
}