package com.fit2081.fit2081_a3_caileen_34375783.NutriCoachTips

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the
 * nutricoach_tips table in the database.

 */
@Dao
interface NutriCoachTipsDao {
    /**
     * Inserts a new [NutriCoachTips] into the database.
     *
     * @param nutriCoachTip The [NutriCoachTips] object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nutriCoachTip: NutriCoachTips)

    /**
     * Retrieves all [NutriCoachTips]s from the database as a [Flow] of lists.
     *
     * @return A [Flow] emitting a list of all [NutriCoachTips]s in the table.
     */
    @Query("SELECT tips FROM nutricoach_tips WHERE userID = :patientId")
    fun getAllTips(patientId: String): Flow<List<String>>
}