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
     * Returns Boolean if phone number matches the userID.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM patient WHERE userID = :userId AND patientPhoneNumber = :phoneNumber)")
    fun isPhoneMatchUser(userId: String, phoneNumber: String): Boolean

    /**
     * Returns Boolean if the password matches the userID.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM patient WHERE userID = :userId AND patientPassword = :password)")
    fun isPasswordMatchUser(userId: String, password: String): Boolean

    /**
     * Adds a name and phone number for a userID.
     */
    @Query("UPDATE patient SET patientName = :name, patientPassword = :password WHERE userID = :userId")
    fun claimAccount(userId: String, name: String, password: String)

}