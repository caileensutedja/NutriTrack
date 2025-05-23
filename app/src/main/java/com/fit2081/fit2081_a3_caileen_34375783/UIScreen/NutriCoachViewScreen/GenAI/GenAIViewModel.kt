package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.GenAI

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.NutriCoachTips.NutriCoachTips
import com.fit2081.fit2081_a3_caileen_34375783.NutriCoachTips.NutriCoachTipsRepository
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

const val apiKey = "AIzaSyCMYsXnBu61hKXC88r-q7thMryuh1dT3M8"

class GenAIViewModel (application: Application): AndroidViewModel(application) {
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val nutriCoachRepository: NutriCoachTipsRepository = NutriCoachTipsRepository(application)
    private val patientRepository: PatientRepository = PatientRepository(application)
    var fruitScore = mutableStateOf<String>("")
    val userId = AuthManager.getPatientId().toString()
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )
     val prompt = "Generate a one sentence encouraging message to help someone improve their fruit " +
             "intake or a fun food tip, they have a fruit score of ${getFruitScore()}/10 (don't mention score)."

    /**
     * To get the fruit score for GenAI tips.
     */
    private fun getFruitScore(){
        viewModelScope.launch {
            val patient = patientRepository.getPatientById(userId)
            fruitScore.value = patient.first().fruitScore
        }
    }

    var allTips: List<String> = emptyList()
    init {
        viewModelScope.launch {
            nutriCoachRepository.getAllTips(userId).collect { tips ->
                allTips = tips
            }
            }
    }

    // Load all tips from the repository
    fun fetchAllTips(): List<String> {
        return allTips
    }

    fun sendPrompt() {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(prompt)
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                    val tip = NutriCoachTips(
                        userID = AuthManager.getPatientId().toString(),
                        tips = outputContent
                    )

                    nutriCoachRepository.insert(tip)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }

}