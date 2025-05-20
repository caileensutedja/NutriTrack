package com.fit2081.fit2081_a3_caileen_34375783.FoodIntake

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
/**
 * Data Access Object (DAO) for interacting with the
 * questionnaire_attempts table in the database.

 */
interface FoodIntakeDao {
    /**
     * To insert the questionnaire result in the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFI(foodIntake: FoodIntake)

    /**
     * Retrieves the questionnaire attempt for a specific patient ID.
     */
    @Query("Select * FROM questionnaire_attempts WHERE userID = :patientId")
    fun getQuizAttemptByPatientId(patientId: String): Flow<List<FoodIntake>>
}


