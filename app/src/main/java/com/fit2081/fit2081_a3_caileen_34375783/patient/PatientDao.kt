package com.fit2081.fit2081_a3_caileen_34375783.patient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    /**
     * To insert a patient on the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPatient(patient: Patient): Long

    /**
     * To retrieve patient on a database based on ID
     */
    @Query("SELECT * FROM patient where userID = :id")
    fun getPatientById(id: String): Flow<Patient>

    /**
     * To retrieve all patients from the database
     */
    @Query("SELECT * FROM patient ORDER BY userID ASC")
    fun getAllPatients(): Flow<List<Patient>>

    /**
     * To delete all patients from the database
     */
    @Query("DELETE FROM patient")
    suspend fun deleteAllPatients()

    /**
     * Get the all patient's user ids from the database.
     */
    @Query("SELECT userID FROM patient ORDER BY CAST(userID AS INTEGER) ASC")
    fun getAllUserIds(): Flow<List<String>>

    /**
     * Returns the phone number of a patient ID.
     */
    @Query("SELECT patientPhoneNumber FROM patient WHERE userID = :userId ")
    fun getPhoneById(userId: String): Flow<String>

    /**
     * Returns the name of a patient ID.
     */
    @Query("SELECT patientName FROM patient WHERE userID = :userId ")
    fun getNameById(userId: String): Flow<String>

    /**
     * Returns the password of a patient ID.
     */
    @Query("SELECT patientPassword FROM patient WHERE userID = :userId")
    fun getPasswordById(userId: String): Flow<String>

    /**
     * Returns the sex of a patient ID.
     */
    @Query("SELECT patientSex FROM patient WHERE userID = :userId ")
    fun getPatientSexById(userId: String): Flow<String>

    /**
     * Returns the totalScore of a patient ID.
     */
    @Query("SELECT totalScore FROM patient WHERE userID = :userId ")
    fun getTotalScoreById(userId: String): Flow<String>

    /**
     * Returns the discretionaryScore of a patient ID.
     */
    @Query("SELECT discretionaryScore FROM patient WHERE userID = :userId ")
    fun getDiscretionaryScoreById(userId: String): Flow<String>

    /**
     * Returns the vegetableScore of a patient ID.
     */
    @Query("SELECT vegetableScore FROM patient WHERE userID = :userId ")
    fun getVegetableScoreById(userId: String): Flow<String>

    /**
     * Returns the fruitScore of a patient ID.
     */
    @Query("SELECT fruitScore FROM patient WHERE userID = :userId ")
    fun getFruitScoreById(userId: String): Flow<String>

    /**
     * Returns the grainsAndCerealScore of a patient ID.
     */
    @Query("SELECT grainsAndCerealScore FROM patient WHERE userID = :userId ")
    fun getGrainsAndCerealScoreById(userId: String): Flow<String>

    /**
     * Returns the wholeGrainsScore of a patient ID.
     */
    @Query("SELECT wholeGrainsScore FROM patient WHERE userID = :userId ")
    fun getWholeGrainsScoreById(userId: String): Flow<String>

    /**
     * Returns the meatAndAltScore of a patient ID.
     */
    @Query("SELECT meatAndAltScore FROM patient WHERE userID = :userId ")
    fun getMeatAndAltScoreById(userId: String): Flow<String>

    /**
     * Returns the dairyAndALtScore of a patient ID.
     */
    @Query("SELECT dairyAndALtScore FROM patient WHERE userID = :userId ")
    fun getDairyAndALtScoreById(userId: String): Flow<String>

    /**
     * Returns the sodiumScore of a patient ID.
     */
    @Query("SELECT sodiumScore FROM patient WHERE userID = :userId ")
    fun getSodiumScoreById(userId: String): Flow<String>

    /**
     * Returns the alcoholScore of a patient ID.
     */
    @Query("SELECT alcoholScore FROM patient WHERE userID = :userId ")
    fun getAlcoholScoreById(userId: String): Flow<String>

    /**
     * Returns the waterScore of a patient ID.
     */
    @Query("SELECT waterScore FROM patient WHERE userID = :userId ")
    fun getWaterScoreById(userId: String): Flow<String>

    /**
     * Returns the sugarScore of a patient ID.
     */
    @Query("SELECT sugarScore FROM patient WHERE userID = :userId ")
    fun getSugarScoreById(userId: String): Flow<String>

    /**
     * Returns the saturatedFatScore of a patient ID.
     */
    @Query("SELECT saturatedFatScore FROM patient WHERE userID = :userId ")
    fun getSaturatedFatScoreById(userId: String): Flow<String>

    /**
     * Returns the unsaturatedFatScore of a patient ID.
     */
    @Query("SELECT unsaturatedFatScore FROM patient WHERE userID = :userId ")
    fun getUnsaturatedFatScoreById(userId: String): Flow<String>

    /**
     * Adds a name and phone number for a userID.
     */
    @Query("UPDATE patient SET patientName = :name, patientPassword = :password WHERE userID = :userId")
    suspend fun claimAccount(userId: String, name: String, password: String)

}