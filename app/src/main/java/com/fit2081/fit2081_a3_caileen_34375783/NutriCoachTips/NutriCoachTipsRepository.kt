package com.fit2081.fit2081_a3_caileen_34375783.NutriCoachTips

import android.content.Context
import com.fit2081.fit2081_a3_caileen_34375783.data.AppDatabase
import kotlinx.coroutines.flow.Flow

class NutriCoachTipsRepository(context: Context) {
    // Create an instance of the Nutricoach DAO
    private val nutriCoachTipsDao = AppDatabase.getDatabase(context).nutriCoachTipsDao()

    /**
     * Insert a tip attempt into the database.
     * @param attempt The NutriCoachTips object to be inserted.
     */
    suspend fun insert(tip: NutriCoachTips) {
        nutriCoachTipsDao.insert(tip)
    }

    /**
     * Retrieve all tips from the database.
     * @return A Flow emitting a list of all quiz attempts.
     */
    fun getAllTips(patientId: String): Flow<List<String>> {
        return nutriCoachTipsDao.getAllTips(patientId)
    }
}

