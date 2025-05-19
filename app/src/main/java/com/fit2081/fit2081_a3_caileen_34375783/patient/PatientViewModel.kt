package com.fit2081.fit2081_a3_caileen_34375783.patient

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PatientViewModel (application: Application): AndroidViewModel(application){
    //Handles all data operations
    private val repository: PatientRepository = PatientRepository(application.applicationContext)

    // Exposes the selected patient as StateFlow
    private val _selectedPatient = MutableStateFlow<Patient?>(null)
//    val selected

    /**
     * Check the initial database, if empty, call the repo.
     */
    fun initialDB(context: Context){
        viewModelScope.launch {
            repository.loadDB(context = context, "data.csv")
        }
    }
//    /**
//     * Insert a patient into the database.
//     *
//     */
//    fun insertPatient(patient: Patient) {
//        viewModelScope.launch {
//            val patient = Patient(
//                patientId = id,
////                blabla
//            )
//            repository.insertPatient(patient)
//        }
//    }

    /**
     * Get the patient by ID from the database
     */
    fun getPatientById(id: String): Flow<Patient> {
        return repository.getPatientById(id)
    }
}