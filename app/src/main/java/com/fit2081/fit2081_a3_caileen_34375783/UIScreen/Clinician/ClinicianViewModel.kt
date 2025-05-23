package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.Clinician

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.NutriCoachTips.NutriCoachTips
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.GenAI.UiState
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.GenAI.apiKey
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClinicianViewModel (application: Application): AndroidViewModel(application) {
    var clinicianKey = mutableStateOf<String>("")
    private val key = "dollar-entry-apples"

    private val patientRepository: PatientRepository =
        PatientRepository(application.applicationContext)

    /**
     * To attempt user Login.
     */
    fun attemptLogin(): Boolean {
        Log.d("debug clinician", "debug clinician " + clinicianKey.value)
        Log.d("debug clinician", "debug clinician key " + key)

        if (clinicianKey.value == key) {
            Toast.makeText(application, "Correct clinician key.", Toast.LENGTH_SHORT).show()
            return clinicianKey.value == key
        }
        Toast.makeText(application, "Invalid clinician key", Toast.LENGTH_SHORT).show()
        return false
    }

    val femaleHEIFAAvg = mutableStateOf<Float?>(null)
    val maleHEIFAAvg = mutableStateOf<Float?>(null)

    init {
        getAvgHEIFAFemale()
        getAvgHEIFAMale()
    }
//    }
    /**
     * Gets the average HEIFA for females
     */
    fun getAvgHEIFAFemale(): Float? {
        viewModelScope.launch {
            femaleHEIFAAvg.value = patientRepository.getAvgHEIFAFemale()
        }
        return femaleHEIFAAvg.value
    }

    /**
     * Gets the average HEIFA for males
     */
    fun getAvgHEIFAMale(): Float? {
        viewModelScope.launch {
            maleHEIFAAvg.value = patientRepository.getAvgHEIFAMale()
        }
        return maleHEIFAAvg.value
    }

    /**
     * To get all patients data including gender and scores.
     */

//    private var allPatientsData = mutableStateOf<List<List<String>>>(emptyList())
//    fun getAllPatientsData() {
//        viewModelScope.launch {
//            patientRepository.getAllPatientsData().collect { data ->
//                allPatientsData.value = data
//                Log.d("DEBUG CLINIC", "DEBUG in get all patients: ${allPatientsData.value}")
//            }
//        }
//        Log.d("DEBUG CLINIC", "DEBUG b4 return get all patients: ${allPatientsData.value}")
//    }
    private val _allPatientsData = MutableStateFlow<List<List<String>>>(emptyList())
    val allPatientsData: StateFlow<List<List<String>>> get() = _allPatientsData

    fun getAllPatientsData() {
        viewModelScope.launch {
            patientRepository.getAllPatientsData().collect { data ->
                _allPatientsData.value = data
                Log.d("DEBUG CLINIC", "DEBUG in get all patients: ${_allPatientsData.value}")
            }
        }
    }


    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )

    var prompt = "Provide 3 short (max 150 words per point) interesting patterns in the data in bullet points: "
    fun findDataPattern() {
        _uiState.value = UiState.Loading
        Log.d("DEBUG CLINIC", "DEBUG CLINIC STRING IS1: ${allPatientsData.value}")
        getAllPatientsData()
        Log.d("DEBUG CLINIC", "DEBUG CLINIC STRING IS2: ${allPatientsData.value}")
        Log.d("DEBUG CLINIC", "DEBUG prompt: ${prompt}")

        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = generativeModel.generateContent(
//                    content {
//                        text(prompt+allPatientsData.value)
//                    }
//                )
//                response.text?.let { outputContent ->
//                    _uiState.value = UiState.Success(outputContent)
//                }
//            } catch (e: Exception) {
//                _uiState.value = UiState.Error(e.localizedMessage ?: "")
//            }
            _allPatientsData.collect { data ->
                if (data.isNotEmpty()) {
                    Log.d("DEBUG CLINIC", "DEBUG CLINIC STRING IS2: $data")
                    Log.d("DEBUG CLINIC", "DEBUG prompt: ${prompt}")

                    // Once the data is collected, proceed to generate the pattern
                    try {
                        val response = generativeModel.generateContent(
                            content {
                                text(prompt + data)
                            }
                        )
                        response.text?.let { outputContent ->
                            _uiState.value = UiState.Success(outputContent)
                        }
                    } catch (e: Exception) {
                        _uiState.value = UiState.Error(e.localizedMessage ?: "")
                    }
                    // Once data is processed, you can break out of the collect loop.
                    return@collect
                }
            }
        }
    }
}