package com.fit2081.fit2081_a3_caileen_34375783.patient

import android.content.Context
import android.util.Log
import com.fit2081.fit2081_a3_caileen_34375783.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class PatientRepository(applicationContext: Context) {
    // Get database instance and insert the repo
    val database = AppDatabase.getDatabase(applicationContext)
    val patientDao = database.patientDao()
    // Stores the current list of patients
//    val allPatients: Flow<List<Patient>> = patientDao.getAllPatients()

    /**
     * Inserts the patient calling Dao
     */
    suspend fun insertPatient(patient: Patient): Long {
        return withContext(Dispatchers.IO) {
            patientDao.insertPatient(patient)
        }
    }

    /**
     * Gets patient by ID through Dao
     */
    suspend fun getPatientById(id: String): Flow<Patient> {
        return patientDao.getPatientById(id)
    }

//    /**
//     * Deletes all patient through Dao
//     */
//    suspend fun deleteAllPatients() = patientDao.deleteAllPatients()

    /**
     * Gets all the current patient userIds.
     */
    suspend fun getAllUserIds(): List<String> {
        Log.d("debug", "list of patients: " + patientDao.getAllUserIds().first())
        // Just return the first emission
        return patientDao.getAllUserIds().first()
    }


    /**
     * Checks if the phone number matches the user ID.
     */
    suspend fun getPhoneById(userId: String): String {
        return patientDao.getPhoneById(userId).first()
    }

    /**
     * Checks if the password matches the user ID.
     */
    suspend fun getPasswordById(userId: String):String {
        return patientDao.getPasswordById(userId).first()
    }

    /**
     * Sets name and phone number for a userID.
     */
    suspend fun claimAccount(userId: String, name: String, password: String) {
        return patientDao.claimAccount(userId, name, password)
    }

    /**
     * Checks if the CSV has been loaded or not through checking if it's empty.
     * returns boolean
     */
    suspend fun loadDB(context: Context, fileName: String) {
        // Checks only the first emission of the flow and stops getting the rest
        val patients = patientDao.getAllPatients().first()
        // Check if that list is empty or not
        if (patients.isEmpty()) {
            Log.d("DEBUG", "empty db in loadDB in Repo")
            // If it's empty, then read the CSV
            readCSVInsert(context, fileName)
        }
    }

    /**
     * Reads the CSV
     */
    private fun readCSVInsert(context: Context, fileName: String) {
        try {
            val inputStream = context.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val header = reader.readLine().split(",") // Removes the header

            //Read CSV by line
            reader.forEachLine { line ->
                // Gets the columns
                val columns = line.split(",")

                // Gets the sex of the patient
                val sex = columns[header.indexOf("Sex")]

                val patient = Patient(
                    userID = columns[header.indexOf("User_ID")],
                    patientPhoneNumber = columns[header.indexOf("PhoneNumber")],
                    patientName = "",
                    patientPassword = "",
                    patientSex = sex,
                    totalScore = columns[header.indexOf("HEIFAtotalscore$sex")],
                    discretionaryScore = columns[header.indexOf("DiscretionaryHEIFAscore$sex")],
                    vegetableScore = columns[header.indexOf("VegetablesHEIFAscore$sex")],
                    fruitScore = columns[header.indexOf("FruitHEIFAscore$sex")],
                    grainsAndCerealScore = columns[header.indexOf("GrainsandcerealsHEIFAscore$sex")],
                    wholeGrainsScore = columns[header.indexOf("WholegrainsHEIFAscore$sex")],
                    meatAndAltScore = columns[header.indexOf("MeatandalternativesHEIFAscore$sex")],
                    dairyAndALtScore = columns[header.indexOf("DairyandalternativesHEIFAscore$sex")],
                    sodiumScore = columns[header.indexOf("SodiumHEIFAscore$sex")],
                    alcoholScore = columns[header.indexOf("AlcoholHEIFAscore$sex")],
                    waterScore = columns[header.indexOf("WaterHEIFAscore$sex")],
                    sugarScore = columns[header.indexOf("SugarHEIFAscore$sex")],
                    saturatedFatScore = columns[header.indexOf("SaturatedFatHEIFAscore$sex")],
                    unsaturatedFatScore = columns[header.indexOf("UnsaturatedFatHEIFAscore$sex")],
                )
                Log.d("DEBUG", "patients is: " + patient)

                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        insertPatient(patient)
                    }
                }
            }
            reader.close()
        } catch (e: Exception) {
        e.printStackTrace()
    }

    }
}