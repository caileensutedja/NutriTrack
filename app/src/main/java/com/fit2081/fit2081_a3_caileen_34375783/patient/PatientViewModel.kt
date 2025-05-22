package com.fit2081.fit2081_a3_caileen_34375783.patient

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import android.util.Log;
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull


class PatientViewModel (application: Application): AndroidViewModel(application){
    //Handles all data operations
    private val repository: PatientRepository = PatientRepository(application.applicationContext)

    var patient = mutableStateOf<Patient?>(null)


    /**
     * Check the initial database, if empty, call the repo.
     */
    fun initialDB(context: Context){
        viewModelScope.launch {
            repository.loadDB(context = context, "data.csv")
        }
    }

    /**
     * Get the patient by ID from the database
     */
     fun getPatientById(userId: String) :
        Flow<Patient> = repository.getPatientById(userId)

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
     * To verify details for account registration.
     */
    val verifyStatus = MutableStateFlow<VerifyStatus?>(null)

    fun verifyRegister(userID: String, userPhone: String) {
        viewModelScope.launch {
            val patient = repository.getPatientById(userID).firstOrNull()
            when {
                patient == null -> verifyStatus.value = VerifyStatus.InvalidID
                patient.patientPhoneNumber != userPhone -> verifyStatus.value = VerifyStatus.InvalidPhone
                patient.patientPassword.isNotEmpty() -> verifyStatus.value = VerifyStatus.AlreadyRegistered
                else -> verifyStatus.value = VerifyStatus.Success
            }
        }
    }

    sealed class VerifyStatus {
        object Success : VerifyStatus()
        object InvalidID : VerifyStatus()
        object InvalidPhone : VerifyStatus()
        object AlreadyRegistered : VerifyStatus()
    }


    /**
     * Sets name and phone number for a userID.
     */
    val claimStatus = MutableStateFlow<ClaimStatus?>(null)

    fun claimRegister(userID: String, name: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (name.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                claimStatus.value = ClaimStatus.MissingFields
                return@launch
            }
            if (password != confirmPassword) {
                claimStatus.value = ClaimStatus.PasswordMismatch
                return@launch
            }

            repository.claimAccount(userID, name, password)
            claimStatus.value = ClaimStatus.Success
        }
    }

    sealed class ClaimStatus {
        object Success : ClaimStatus()
        object MissingFields : ClaimStatus()
        object PasswordMismatch : ClaimStatus()
    }



}