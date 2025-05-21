package com.fit2081.fit2081_a3_caileen_34375783.FoodIntake

import android.content.Context
import androidx.room.Query
import androidx.room.Update
import com.fit2081.fit2081_a3_caileen_34375783.data.AppDatabase
import com.fit2081.fit2081_a3_caileen_34375783.patient.Patient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class FoodIntakeRepository(context: Context) {
    // Create an instance of the FoodIntake DAO
    private val foodIntakeDao =
        AppDatabase.getDatabase(context).foodIntakeDao()

//    /**
//     * Insert a new questionnaire attempt into the database.
//     * @param attempt The QuizAttempt object to be inserted.
//     */
//    suspend fun insertFI(foodIntake: FoodIntake) {
//        foodIntakeDao.insertFI(foodIntake)
//    }

    /**
     * To insert/update the food intake attempt.
     */
    suspend fun attemptFoodIntake(foodIntake: FoodIntake) {
        val existingAttempt = foodIntakeDao.getQuizAttemptByPatientId(foodIntake.userID).firstOrNull()

        if (existingAttempt != null) {
            // Copy the existing ID to update the respective food intake data
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

//    /**
//     * To update the intake attempt.
//     */
//    @Update
//    suspend fun update(foodIntake: FoodIntake)
//
//
//    /**
//     * Retrieve quiz attempts for a specific student from the database.
//     * @param studentId The ID of the student.
//     * @return A Flow emitting a list of quiz attempts for the specified student.
//     */
//    fun getQuizAttemptByPatientId(studentId: String): Flow<List<FoodIntake>> =
//        foodIntakeDao.getQuizAttemptByPatientId(studentId)
}
