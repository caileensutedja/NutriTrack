package com.fit2081.fit2081_a3_caileen_34375783.FoodIntake

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for the FoodIntake Table Model
 */
@Entity(tableName = "questionnaire_attempts")
data class FoodIntake(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /**
     * Identifier of the patient who attempted the quiz.
     */
    val userID: String, //Patient's ID

    val fruit: Boolean,
    val vegetable: Boolean,
    val grains: Boolean,
    val redMeat: Boolean,
    val seafood: Boolean,
    val poultry: Boolean,
    val fish: Boolean,
    val eggs: Boolean,
    val nutsSeeds: Boolean,
    val persona: String,
    val timeMeal: String,
    val timeSleep: String,
    val timeWakeup: String
    )
