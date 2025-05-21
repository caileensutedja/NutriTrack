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
     * Sets name and phone number for a userID.
     */
    suspend fun claimAccount(userId: String, name: String, password: String){
        return repository.claimAccount(userId, name, password)
    }

}