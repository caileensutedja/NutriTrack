package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.WelcomeScreen

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.FoodIntake.FoodIntakeRepository
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientRepository
import kotlinx.coroutines.launch

class WelcomeViewModel (application: Application): AndroidViewModel(application){
    private val repository: PatientRepository = PatientRepository(application.applicationContext)

    /**
     * Check the initial database, if empty, call the repo.
     */
    fun initialDB(context: Context){
        viewModelScope.launch {
            repository.loadDB(context = context, "data.csv")
        }
    }

    private val foodRepository = FoodIntakeRepository(application.applicationContext)
    var hasAttempt = mutableStateOf<Boolean>(false)
    /**
     * Check if user has a questionnaire response.
     */
//    fun checkQuestionnaire(patientId: String?) {
//        Log.d("debug in wm", "debug in wm in check questionnaire vm patient id is " + patientId)
//        if (patientId != null) {
//            Log.d("debug in wm", "patinet id not null")
//
//            viewModelScope.launch {
//                Log.d("debug in wm", "patinet id not null1 before " +hasAttempt.value )
//                hasAttempt.value = foodRepository.getAttemptByIDBool(patientId)
//                Log.d("debug in wm", "patinet id not null1 after " +hasAttempt.value )
//            }
//        }
//    }
    fun checkQuestionnaire(patientId: String?, onResult: (Boolean) -> Unit) {
        if (patientId != null) {
            viewModelScope.launch {
                val result = foodRepository.getAttemptByIDBool(patientId)
                hasAttempt.value = result
                Log.d("debug in vm", "Result is $result")
                onResult(result) // call back with the result
            }
        } else {
            onResult(false)
        }
    }


}