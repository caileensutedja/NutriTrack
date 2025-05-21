package com.fit2081.fit2081_a3_caileen_34375783.FoodIntake

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
     * To get the food intake questionnaire attempt by the patient id.
     */
    @Query("SELECT * FROM questionnaire_attempts WHERE userID = :patientId LIMIT 1")
    fun getAttemptByPatientId(patientId: String): FoodIntake?

    /**
     * To update the intake attempt.
     */
    @Update
    suspend fun update(foodIntake: FoodIntake)


    /**
     * Retrieves the questionnaire attempt for a specific patient ID.
     */
    @Query("Select * FROM questionnaire_attempts WHERE userID = :patientId")
    fun getQuizAttemptByPatientId(patientId: String): Flow<FoodIntake>
}


