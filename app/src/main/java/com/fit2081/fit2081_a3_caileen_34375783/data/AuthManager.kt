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

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

object AuthManager {
    private val USER_ID_KEY = stringPreferencesKey("user_id")
    var userId = mutableStateOf<String?>(null)
        private set

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

    fun login(context: Context, userId: String) {
        this.userId.value = userId
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { preferences ->
                preferences[USER_ID_KEY] = userId
            }
        }
    }

    fun logout(context: Context) {
        this.userId.value = null
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { preferences ->
                preferences.remove(USER_ID_KEY)
            }
        }
    }

    fun getPatientId(): String? {
        return userId.value
    }
}