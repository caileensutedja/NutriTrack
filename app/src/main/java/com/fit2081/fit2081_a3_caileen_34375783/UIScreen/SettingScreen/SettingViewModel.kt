package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.SettingScreen

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class SettingViewModel (application: Application): AndroidViewModel(application){
    //Handles all data operations
    private val repository: PatientRepository = PatientRepository(application.applicationContext)
    /**
     * Function to get the phone number of the user.
     */
    private var phoneNumber = mutableStateOf("")
    fun getPhoneNumber(userId: String) : String {
        viewModelScope.launch {
            // Gets the patient from the repository
            val patient = repository.getPatientById(userId).firstOrNull()
            if (patient != null) {
                phoneNumber.value = patient.patientPhoneNumber
            } else {
                // If patient data is not found
                phoneNumber.value = "No patient data found."
            }
        }
        return phoneNumber.value
    }

    /**
     * Function to get the name of the user.
     */
    var name = mutableStateOf("")
    fun getName(userId: String) : String {
        viewModelScope.launch {
            // Fetch the patient from the repository
            val patient = repository.getPatientById(userId).firstOrNull()
            if (patient != null) {
                name.value = patient.patientName
            } else {
                // If patient data is not found
                name.value = "No patient data found."
            }
        }
        return name.value
    }
}