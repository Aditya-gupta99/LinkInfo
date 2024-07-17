package com.sparklead.linkinfo.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefManager @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "LinkInfo")
    private val dataStore = context.dataStore


    suspend fun saveStringValue(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    fun readStringValue(key: String): Flow<String> {
        val dataStoreKey = stringPreferencesKey(key)
        return dataStore.data
            .catch {
                when (it) {
                    is IOException -> {
                        emit(emptyPreferences())
                    }

                    else -> throw it
                }
            }
            .map { preferences ->
                preferences[dataStoreKey] ?: ""
            }
    }

}