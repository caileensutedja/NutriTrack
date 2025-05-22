package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.InsightScreen

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class InsightViewModel (application: Application): AndroidViewModel(application) {
    //Handles all data operations
    private val repository: PatientRepository = PatientRepository(application.applicationContext)

    /**
     * Function to get all insights of user through list of lists..
     */
    var insightsData = mutableStateOf<List<List<String>>>(emptyList())
    fun getInsightsById(userId: String) : List<List<String>> {
        viewModelScope.launch {
            // Fetch the patient from the repository
            val patient = repository.getPatientById(userId).firstOrNull()

            if (patient != null) {
                // Set the insights data (food categories and scores)
                insightsData.value = listOf(
                    listOf("Vegetables", patient.vegetableScore, "10"),
                    listOf("Fruits", patient.fruitScore, "10"),
                    listOf("Grains and Cereals", patient.grainsAndCerealScore, "5"),
                    listOf("Whole Grains", patient.wholeGrainsScore, "5"),
                    listOf("Meat and Alternatives", patient.meatAndAltScore, "10"),
                    listOf("Dairy", patient.dairyAndALtScore, "10"),
                    listOf("Water", patient.waterScore, "5"),
                    listOf("Saturated Fats", patient.saturatedFatScore, "5"),
                    listOf("Unsaturated Fats", patient.unsaturatedFatScore, "5"),
                    listOf("Sodium", patient.sodiumScore, "10"),
                    listOf("Sugar", patient.sugarScore, "10"),
                    listOf("Alcohol", patient.alcoholScore, "5"),
                    listOf("Discretionary Foods", patient.discretionaryScore, "10"))
            } else {
                // If patient data is not found
                insightsData.value = emptyList()
            }
        }
        return insightsData.value
    }

    /**
     * Function to get the total score of the user within a message to share it.
     */
    var totalScoreMessage = mutableStateOf<String>("")
    // Function to get the total score message for the patient
    fun getTotalScoreMessage(userId: String) : String {
        viewModelScope.launch {
            // Fetch the patient from the repository
            val patient = repository.getPatientById(userId).firstOrNull()

            if (patient != null) {
                    // Set the total score message
                    totalScoreMessage.value = "My total score is ${patient.totalScore}/100."

            } else {
                // If patient data is not found
                totalScoreMessage.value = "No patient data found."
            }
        }
        return totalScoreMessage.value
    }

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
}