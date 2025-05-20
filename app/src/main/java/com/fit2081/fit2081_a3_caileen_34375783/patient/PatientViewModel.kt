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

    var totalScore = mutableStateOf<String>("")
    var name = mutableStateOf<String>("")
    var phoneNumber = mutableStateOf<String>("")
    var password = mutableStateOf<String>("")


    /**
     * Check the initial database, if empty, call the repo.
     */
    fun initialDB(context: Context){
//        Log.d("DEBUG", "ran through initialDB in ViewModel")
        viewModelScope.launch {
//            Log.d("DEBUG", "launching through initialDB in ViewModel")
            repository.loadDB(context = context, "data.csv")
        }
    }

    /**
     * Get the patient by ID from the database
     */
    suspend fun getPatientById(id: String): Flow<Patient> {
        return repository.getPatientById(id)
    }

    /**
     * Get all userIDs present in the database
     */
    suspend fun getAllUserIds(): List<String> {
        return repository.getAllUserIds()
    }

    /**
     * Gets the phone number that matches the user ID.
     */
    fun getPhoneById(userId: String) {
        viewModelScope.launch {
            val score = repository.getPhoneById(userId)
            phoneNumber.value = score
        }
    }

    /**
     * Gets the name that matches the user ID.
     */
    fun getNameById(userId: String) {
        viewModelScope.launch {
            val score = repository.getNameById(userId)
            name.value = score
        }
    }


    /**
     * Checks if the password matches the user ID.
     */
    fun getPasswordById(userId: String) {
        viewModelScope.launch {
            val score = repository.getPasswordById(userId)
            password.value = score
        }
    }

    /**
     * Gets the sex that matches the user ID.
     */
    suspend fun getPatientSexId(userId: String): String {
        return repository.getPatientSexId(userId)
    }

    /**
     * Gets the total score that matches the user ID.
     */
    fun getTotalScoreById(userId: String) {
        viewModelScope.launch {
            val score = repository.getTotalScoreById(userId)
            totalScore.value = score
        }
    }

    /**
     * Gets the discretionary score that matches the user ID.
     */
    suspend fun getDiscretionaryScoreById(userId: String): String {
        return repository.getDiscretionaryScoreById(userId)
    }

    /**
     * Gets the vegetable score that matches the user ID.
     */
    suspend fun getVegetableScoreById(userId: String): String {
        return repository.getVegetableScoreById(userId)
    }

    /**
     * Gets the fruit score that matches the user ID.
     */
    suspend fun getFruitScoreById(userId: String): String {
        return repository.getFruitScoreById(userId)
    }

    /**
     * Gets the grains and cereal score that matches the user ID.
     */
    suspend fun getGrainsAndCerealScoreById(userId: String): String {
        return repository.getGrainsAndCerealScoreById(userId)
    }

    /**
     * Gets the whole grains score that matches the user ID.
     */
    suspend fun getWholeGrainsScoreById(userId: String): String {
        return repository.getWholeGrainsScoreById(userId)
    }

    /**
     * Gets the meat and alt score that matches the user ID.
     */
    suspend fun getMeatAndAltScoreById(userId: String): String {
        return repository.getMeatAndAltScoreById(userId)
    }

    /**
     * Gets the dairy and alt score that matches the user ID.
     */
    suspend fun getDairyAndALtScoreById(userId: String): String {
        return repository.getDairyAndALtScoreById(userId)
    }

    /**
     * Gets the sodium score that matches the user ID.
     */
    suspend fun getSodiumScoreById(userId: String): String {
        return repository.getSodiumScoreById(userId)
    }

    /**
     * Gets the alcohol score that matches the user ID.
     */
    suspend fun getAlcoholScoreById(userId: String): String {
        return repository.getAlcoholScoreById(userId)
    }

    /**
     * Gets the water score that matches the user ID.
     */
    suspend fun getWaterScoreById(userId: String): String {
        return repository.getWaterScoreById(userId)
    }

    /**
     * Gets the sugar score that matches the user ID.
     */
    suspend fun getSugarScoreById(userId: String): String {
        return repository.getSugarScoreById(userId)
    }

    /**
     * Gets the saturated score that matches the user ID.
     */
    suspend fun getSaturatedFatScoreById(userId: String): String {
        return repository.getSaturatedFatScoreById(userId)
    }

    /**
     * Gets the unsaturated score that matches the user ID.
     */
    suspend fun getUnsaturatedFatScoreById(userId: String): String {
        return repository.getUnsaturatedFatScoreById(userId)
    }

    /**
     * Sets name and phone number for a userID.
     */
    suspend fun claimAccount(userId: String, name: String, password: String){
        return repository.claimAccount(userId, name, password)
    }

}