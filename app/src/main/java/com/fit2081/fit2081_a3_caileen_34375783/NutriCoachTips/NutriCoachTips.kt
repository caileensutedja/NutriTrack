package com.fit2081.fit2081_a3_caileen_34375783.NutriCoachTips

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "nutricoach_tips")
data class NutriCoachTips(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /**
     * Identifier of the patient who attempted the quiz.
     */
    val userID: String, //Patient's ID

    /**
     * Tips for the user.
     */
    val tips: String
)
