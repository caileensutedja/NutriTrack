package com.fit2081.fit2081_a3_caileen_34375783.FoodIntake

import android.content.Context
import android.util.Log
import com.fit2081.fit2081_a3_caileen_34375783.data.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class FoodIntakeRepository(context: Context) {
    // Create an instance of the FoodIntake DAO
    private val foodIntakeDao =
        AppDatabase.getDatabase(context).foodIntakeDao()

    /**
     * To insert/update the food intake attempt.
     */
    suspend fun attemptFoodIntake(foodIntake: FoodIntake) {
        val existingAttempt = foodIntakeDao.getQuizAttemptByPatientId(foodIntake.userID).firstOrNull()
        if (existingAttempt != null) {
            // Copy the existing ID to update the respective food intake data, and re-insert it.
            val updated = foodIntake.copy(id = existingAttempt.id)
            foodIntakeDao.update(updated)
        } else {
            // Insert a new food intake data
            foodIntakeDao.insertFI(foodIntake)
        }
    }

    /**
     * To get the food intake questionnaire attempt by the patient id.
     */
    fun getQuizAttemptByPatientId(patientId: String): Flow<FoodIntake> =
        foodIntakeDao.getQuizAttemptByPatientId(patientId)

    /**
     * To check if patient has an attempt or not.
     */
     suspend fun getAttemptByIDBool(patientId: String) : Boolean {
        val existingAttempt = foodIntakeDao.getQuizAttemptByPatientId(patientId).firstOrNull()
        Log.d("debug vm", "debug vm in repo is " + existingAttempt)
        return existingAttempt != null
    }
}
