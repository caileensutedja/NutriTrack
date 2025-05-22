package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.LoginScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.patient.Patient
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class LoginViewModel (application: Application): AndroidViewModel(application){
    //Handles all data operations
    private val repository: PatientRepository = PatientRepository(application.applicationContext)

    /**
     * Get all userIDs present in the database
     */
    fun getAllUserIds():
            Flow<List<String>> = repository.getAllUserIds()

    /**
     * To attempt and login.
     */
    var loginResult = MutableStateFlow<LoginResult?>(null)

    fun login(userId: String, password: String) {
        viewModelScope.launch {
            val result = repository.checkLogin(userId, password)
            loginResult.value = result
        }
    }

    sealed class LoginResult {
        data class Success(val patient: Patient): LoginResult()
        object IncorrectPassword: LoginResult()
        object AccountNotClaimed: LoginResult()
        object AccountNotFound: LoginResult()
    }

    /**
     * To attempt register.
     */
    var registerUserDataResult = MutableStateFlow<RegisterResult?>(null)

    fun registerUserDataValidation(userId: String, password: String) {
        viewModelScope.launch {
            val result = repository.verifyRegister(userId, password)
            registerUserDataResult.value = result
        }
    }
    sealed class RegisterResult {
        object Success : RegisterResult()
        object InvalidPhone : RegisterResult()
        object AlreadyRegistered : RegisterResult()
        object AccountNotFound: RegisterResult()
    }

    /**
     * Sets name and phone number for a userID.
     */
    val claimAccount = true // For the UI logic.
    fun claimRegister(userID: String, name: String, password: String) {
        viewModelScope.launch {
            repository.claimAccount(userID, name, password)
        }
    }
}
