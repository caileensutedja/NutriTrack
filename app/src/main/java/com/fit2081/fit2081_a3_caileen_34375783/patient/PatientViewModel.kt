package com.fit2081.fit2081_a3_caileen_34375783.patient

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import android.util.Log;


class PatientViewModel (application: Application): AndroidViewModel(application){
    //Handles all data operations
    private val repository: PatientRepository = PatientRepository(application.applicationContext)

    // Exposes the selected patient as StateFlow
//    private val _selectedPatient = MutableStateFlow<Patient?>(null)

    /**
     * Check the initial database, if empty, call the repo.
     */
    fun initialDB(context: Context){
        Log.d("DEBUG", "ran through initialDB in ViewModel")
        viewModelScope.launch {
            Log.d("DEBUG", "launching through initialDB in ViewModel")
            repository.loadDB(context = context, "data.csv")
        }
    }

    /**
     * Get the patient by ID from the database
     */
    fun getPatientById(id: String): Flow<Patient> {
        return repository.getPatientById(id)
    }

    /**
     * Get all userIDs present in the database
     */
    suspend fun getAllUserIds(): List<String> {
//        viewModelScope.launch {
//            val userIds = repository.getAllUserIds()
//        }
        return repository.getAllUserIds()
    }

    /**
     * Checks if the phone number matches the user ID.
     */
    suspend fun getPhoneById(userId: String) : String {
        return repository.getPhoneById(userId)
    }

    /**
     * Checks if the password matches the user ID.
     */
    suspend fun getPasswordById(userId: String) : String {
        return repository.getPasswordById(userId)
    }

    /**
     * Sets name and phone number for a userID.
     */
    suspend fun claimAccount(userId: String, name: String, password: String){
        return repository.claimAccount(userId, name, password)
    }

}