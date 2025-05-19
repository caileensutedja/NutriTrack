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
}