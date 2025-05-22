package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.WelcomeScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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

}