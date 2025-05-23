package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class NutriCoachViewModel (application: Application): AndroidViewModel(application){
    //Handles all data operations
    private val repository: PatientRepository = PatientRepository(application.applicationContext)

    val isOptimal = mutableStateOf<Boolean>(false)
    fun isFruitScoreOptimal(userId: String) : Boolean {
        viewModelScope.launch {
            // Fetch the patient from the repository
            val patient = repository.getPatientById(userId).firstOrNull()

            val fruitVariety = patient?.fruitVariety?.toFloatOrNull() ?: 0f
            val fruitServingSize = patient?.fruitServeSize?.toFloatOrNull() ?: 0f
            val totalFruitOptimal = fruitVariety + fruitServingSize

            // Check if it is optimal or not.
            if (fruitVariety >=2 &&
                fruitServingSize >= 2 &&
                totalFruitOptimal >= 5) {
                Log.d("TRY nutricoach", "is optimal pass")

                isOptimal.value = true
            } else {
                Log.d("TRY nutricoach", "is false pass")
                isOptimal.value = false
            }
        }
        return isOptimal.value
    }
}