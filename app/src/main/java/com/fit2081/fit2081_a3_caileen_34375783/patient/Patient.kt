package com.fit2081.fit2081_a3_caileen_34375783.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for the Patient Table Model
 */
@Entity(tableName = "patient")
data class Patient(
    //Primary Key
    @PrimaryKey
    val userID: String, //Patient's ID
    val patientPhoneNumber: String, //Patient's phone number
    val patientName: String, //Patient's name
    val patientPassword: String, //Patient's password
    val patientSex: String, //Patient's sex
    val totalScore: String, // HEIFAtotalscoreMale
    val discretionaryScore: String, //DiscretionaryHEIFAscoreMale
    val vegetableScore: String, //VegetablesHEIFAscoreMale
    val fruitScore: String, //FruitHEIFAscoreMale
    val grainsAndCerealScore: String, // GrainsandcerealsHEIFAscoreMale
    val wholeGrainsScore: String, //WholegrainsHEIFAscoreMale
    val meatAndAltScore: String, //MeatandalternativesHEIFAscoreMale
    val dairyAndALtScore: String, //DairyandalternativesHEIFAscoreMale
    val sodiumScore: String, //SodiumHEIFAscoreMale
    val alcoholScore: String, //AlcoholHEIFAscoreMale
    val waterScore: String, //WaterHEIFAscoreMale
    val sugarScore: String, //SugarHEIFAscoreMale
    val saturatedFatScore: String, //SaturatedFatHEIFAscoreMale
    val unsaturatedFatScore: String //UnsaturatedFatHEIFAscoreMale
)
