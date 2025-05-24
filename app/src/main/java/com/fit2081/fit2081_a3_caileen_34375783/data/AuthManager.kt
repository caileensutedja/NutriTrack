package com.fit2081.fit2081_a3_caileen_34375783.data

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Using Preferences Data Store, storing asynchronously as key-value pairs
val Context.dataStore by preferencesDataStore(name = "auth_prefs")

/**
 * To store the current status of logged in/out user.
 * Will store it although app is refreshed, until logged out.
 */
object AuthManager {
    private val USER_ID_KEY = stringPreferencesKey("user_id")
    var userId = mutableStateOf<String?>(null)
        private set

    /**
     * Initialises the userId for persistent storage.
     */
    fun init(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val storedUserId = context.dataStore.data
                .catch { exception ->
                    if (exception is IOException) emit(emptyPreferences())
                    else throw exception
                }
                .map { preferences -> preferences[USER_ID_KEY] }
                .first()

            userId.value = storedUserId
        }
    }

    /**
     * Replaces the userId with a specific string (the id).
     */
    fun login(context: Context, userId: String) {
        this.userId.value = userId
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { preferences ->
                preferences[USER_ID_KEY] = userId
            }
        }
    }

    /**
     * Removes the userId data to null
     */
    fun logout(context: Context) {
        this.userId.value = null
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { preferences ->
                preferences.remove(USER_ID_KEY)
            }
        }
    }

    /**
     * Gets the current userId stored.
     */
    fun getPatientId(): String? {
        return userId.value
    }
}