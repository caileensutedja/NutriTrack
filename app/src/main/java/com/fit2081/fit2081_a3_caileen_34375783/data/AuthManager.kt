package com.fit2081.fit2081_a3_caileen_34375783.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object AuthManager {
    val _userId: MutableState<String?> = mutableStateOf(null)

    fun login(userId: String) {
        _userId.value = userId
    }

    fun logout() {
        _userId.value = null
    }

    fun getPatientId(): String? {
        return _userId.value
    }
}