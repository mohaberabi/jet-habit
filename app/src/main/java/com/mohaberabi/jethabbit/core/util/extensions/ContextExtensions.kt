package com.mohaberabi.jethabbit.core.util.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mohaberabi.jethabbit.core.util.const.AppConst


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(AppConst.DATASTORE_NAME)