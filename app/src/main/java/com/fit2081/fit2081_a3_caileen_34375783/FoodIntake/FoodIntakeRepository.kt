package com.fit2081.fit2081_a3_caileen_34375783.FoodIntake

import android.content.Context
import com.fit2081.fit2081_a3_caileen_34375783.data.AppDatabase
import kotlinx.coroutines.flow.Flow

class FoodIntakeRepository(context: Context) {
    // Create an instance of the FoodIntake DAO
    private val foodIntakeDao =
        AppDatabase.getDatabase(context).foodIntakeDao()

    /**
     * Insert a new questionnaire attempt into the database.
     * @param attempt The QuizAttempt object to be inserted.
     */
    suspend fun insertFI(foodIntake: FoodIntake) {
        foodIntakeDao.insertFI(foodIntake)
    }

    /**
     * Retrieve quiz attempts for a specific student from the database.
     * @param studentId The ID of the student.
     * @return A Flow emitting a list of quiz attempts for the specified student.
     */
    fun getQuizAttemptByPatientId(studentId: String): Flow<List<FoodIntake>> =
        foodIntakeDao.getQuizAttemptByPatientId(studentId)
}
