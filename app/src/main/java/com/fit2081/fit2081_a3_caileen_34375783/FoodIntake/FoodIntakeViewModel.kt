package com.fit2081.fit2081_a3_caileen_34375783.FoodIntake

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel class for managing quiz attempts data.
 * This class interacts with the QuizAttemptsRepository to perform database operations
 * and provides data to the UI.
 *
 * @param context The application context.
 */
class FoodIntakeViewModel(application: Application) : AndroidViewModel(application) {

    private val foodIntakeRepository: FoodIntakeRepository =
        FoodIntakeRepository(application.applicationContext)

    /**
     * Inserts a new quiz attempt into the database.
     * @param quizAttempt The quiz attempt to be inserted.
     */
    fun attemptFoodIntake(foodIntake: FoodIntake) {
        viewModelScope.launch { foodIntakeRepository.attemptFoodIntake(foodIntake) }
    }

    /**
     * Retrieves questionnaire attempts for a specific patient ID.
     * @param patientId The ID of the patient.
     * @return A Flow emitting a list of quiz attempts for the given student ID.
     */
    fun getQuizAttemptByPatientId(patientId: String):
            Flow<FoodIntake> = foodIntakeRepository.getQuizAttemptByPatientId(patientId)

}

