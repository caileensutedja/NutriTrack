package com.fit2081.fit2081_a3_caileen_34375783.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fit2081.fit2081_a3_caileen_34375783.patient.Patient
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientDao
import com.fit2081.fit2081_a3_caileen_34375783.FoodIntake.FoodIntake
import com.fit2081.fit2081_a3_caileen_34375783.FoodIntake.FoodIntakeDao

/**
 * This is the main app database.
 */
@Database(
    entities = [Patient::class, FoodIntake::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao

    /**
     * Ensures only 1 instance of the database is created throughout the app.
     */
    companion object {
        // Volatile ensures INSTANCE is always up-to-date
        // and the same the same for all execution threads.
        @Volatile
        private var INSTANCE : AppDatabase? = null

        // Gets the singleton database instance, creating it if it doesn't exist.
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "patients_database"
//                )
//                    //Avoids crashing and delete existing, creating new updated schema
//                    .fallbackToDestructiveMigration()
//                    .build()
//                //Assign the newly created instance to INSTANCE
//                INSTANCE = instance
//                instance // Return the instance


                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .build()
                    .also { INSTANCE = it }
            }
        }

//        fun getDatabase(context: Context): CollegeDatabase {
//            return Instance ?: synchronized(this) {
//                Room.databaseBuilder(context, CollegeDatabase::class.java, "item_database")
//                    .build()
//                    .also { Instance = it }
//            }
        }
    }
//}