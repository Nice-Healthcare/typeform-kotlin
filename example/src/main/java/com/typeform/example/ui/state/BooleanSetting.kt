package com.typeform.example.ui.state

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BooleanSetting(
    private val context: Context,
    preferenceKey: String,
) {
    private val key: Preferences.Key<Boolean> = booleanPreferencesKey(preferenceKey)

    fun isEnabled(defaultValue: Boolean = false): Flow<Boolean> =
        context
            .settingsDataStore
            .data
            .map { preferences ->
                preferences[key] ?: defaultValue
            }

    suspend fun setEnabled(enabled: Boolean) =
        context
            .settingsDataStore
            .edit { preferences ->
                preferences[key] = enabled
            }
}
