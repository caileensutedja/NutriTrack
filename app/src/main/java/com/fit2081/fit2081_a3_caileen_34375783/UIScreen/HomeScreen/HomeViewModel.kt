package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class HomeViewModel (application: Application): AndroidViewModel(application){
    //Handles all data operations
    private val repository: PatientRepository = PatientRepository(application.applicationContext)
    /**
     * Function to get the total score of the user.
     */
    var totalScore = mutableStateOf<String>("")
    fun getTotalScore(userId: String) : String {
        viewModelScope.launch {
            // Fetch the patient from the repository
            val patient = repository.getPatientById(userId).firstOrNull()
            if (patient != null) {
                totalScore.value = patient.totalScore
            } else {
                // If patient data is not found
                totalScore.value = "No patient data found."
            }
        }
        return totalScore.value
    }

    /**
     * Function to get the total score of the user.
     */
    var name = mutableStateOf<String>("")
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