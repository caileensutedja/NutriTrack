package com.fit2081.fit2081_a3_caileen_34375783.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fit2081.fit2081_a3_caileen_34375783.patient.Patient
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientDao
import com.fit2081.fit2081_a3_caileen_34375783.FoodIntake.FoodIntake
import com.fit2081.fit2081_a3_caileen_34375783.FoodIntake.FoodIntakeDao
import com.fit2081.fit2081_a3_caileen_34375783.NutriCoachTips.NutriCoachTips
import com.fit2081.fit2081_a3_caileen_34375783.NutriCoachTips.NutriCoachTipsDao

/**
 * This is the main app database.
 */
@Database(
    entities = [
        Patient::class,
        FoodIntake::class,
        NutriCoachTips::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao
    abstract fun nutriCoachTipsDao(): NutriCoachTipsDao

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
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}