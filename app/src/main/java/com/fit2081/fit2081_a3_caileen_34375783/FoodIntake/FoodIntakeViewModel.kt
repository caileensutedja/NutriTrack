package com.fit2081.fit2081_a3_caileen_34375783.FoodIntake

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
class FoodIntakeViewModel(context: Context) : ViewModel() {

    private val foodIntakeRepository: FoodIntakeRepository =
        FoodIntakeRepository(context)
//
//    /**
//     * Flow of all quiz attempts.
//     */
//    val allAttempts: Flow<List<QuizAttempt>> = foodIntakeRepository.allAttempts;

    /**
     * Inserts a new quiz attempt into the database.
     * @param quizAttempt The quiz attempt to be inserted.
     */
    fun insertFoodIntakeAttempt(foodIntake: FoodIntake) {
        viewModelScope.launch { foodIntakeRepository.insertFI(foodIntake) }
    }

    /**
     * Retrieves questionnaire attempts for a specific patient ID.
     * @param patientId The ID of the patient.
     * @return A Flow emitting a list of quiz attempts for the given student ID.
     */
    fun getQuizAttemptByPatientId(patientId: String):
            Flow<List<FoodIntake>> = foodIntakeRepository.getQuizAttemptByPatientId(patientId)

    /**
     *
     */
    class FoodIntakeViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        // Use application context to avoid memory leaks
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FoodIntakeViewModel(context) as T
    }
}

