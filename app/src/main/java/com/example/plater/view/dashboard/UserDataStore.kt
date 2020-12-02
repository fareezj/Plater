package com.example.plater.view.dashboard

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore (context: Context) {

    private val applicationContext = context.applicationContext
    private val dataStore:  DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
                name = "current_user_details"
        )
    }

    suspend fun saveUsername(name: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USERNAME] = name
        }
    }

    suspend fun saveUserState(boolean: Boolean){
        dataStore.edit { preferences ->
            preferences[KEY_USER_STATE] = boolean
        }
    }

    val getUsername: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_USERNAME]
        }

    val getUserState: Flow<Boolean?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_USER_STATE]
        }

    companion object {
        val KEY_USERNAME = preferencesKey<String>("key_username")
        val KEY_USER_STATE = preferencesKey<Boolean>("key_user_state")
    }

}