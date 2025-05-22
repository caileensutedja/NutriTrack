package com.fit2081.fit2081_a3_caileen_34375783.patient

import android.content.Context
import android.util.Log
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.LoginScreen.LoginViewModel
import com.fit2081.fit2081_a3_caileen_34375783.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class PatientRepository(applicationContext: Context) {
    // Get database instance and insert the repo
    val database = AppDatabase.getDatabase(applicationContext)
    val patientDao = database.patientDao()

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
    fun getPatientById(id: String): Flow<Patient> =
        patientDao.getPatientById(id)

    /**
     * Gets all the current patient userIds.
     */
    fun getAllUserIds(): Flow<List<String>> =
        patientDao.getAllUserIds()

    /**
     * Sets name and phone number for a userID.
     */
    suspend fun claimAccount(userId: String, name: String, password: String) {
        return patientDao.claimAccount(userId, name, password)
    }


    /**
     * Function to check from the database if the user and password matches.
     */
    suspend fun checkLogin(userId: String, password: String): LoginViewModel.LoginResult {
        val patient = patientDao.getPatientById(userId).firstOrNull()

        return when {
            patient == null -> LoginViewModel.LoginResult.AccountNotFound
            patient.patientPassword.isEmpty() -> LoginViewModel.LoginResult.AccountNotClaimed
            patient.patientPassword == password -> LoginViewModel.LoginResult.Success(patient)
            else -> LoginViewModel.LoginResult.IncorrectPassword
        }
    }

    /**
     * Function to verify details to claim register account.
     */
    suspend fun verifyRegister(userID: String, userPhone: String) : LoginViewModel.RegisterResult {
        val patient = patientDao.getPatientById(userID).firstOrNull()
        return when {
            patient == null -> LoginViewModel.RegisterResult.AccountNotFound
            patient.patientPassword != "" -> LoginViewModel.RegisterResult.AlreadyRegistered
            patient.patientPhoneNumber == userPhone -> LoginViewModel.RegisterResult.Success
            else -> LoginViewModel.RegisterResult.InvalidPhone
        }
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
                    fruitServeSize = columns[header.indexOf("Fruitservesize")],
                    fruitVariety = columns[header.indexOf("Fruitvariationsscore")]
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