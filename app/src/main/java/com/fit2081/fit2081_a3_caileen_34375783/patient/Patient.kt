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
    val totalScore: String, // HEIFAtotalscore
    val discretionaryScore: String, //DiscretionaryHEIFAscore
    val vegetableScore: String, //VegetablesHEIFAscore
    val fruitScore: String, //FruitHEIFAscore
    val grainsAndCerealScore: String, // GrainsandcerealsHEIFAscore
    val wholeGrainsScore: String, //WholegrainsHEIFAscore
    val meatAndAltScore: String, //MeatandalternativesHEIFAscore
    val dairyAndALtScore: String, //DairyandalternativesHEIFAscore
    val sodiumScore: String, //SodiumHEIFAscore
    val alcoholScore: String, //AlcoholHEIFAscore
    val waterScore: String, //WaterHEIFAscore
    val sugarScore: String, //SugarHEIFAscore
    val saturatedFatScore: String, //SaturatedFatHEIFAscore
    val unsaturatedFatScore: String, //UnsaturatedFatHEIFAscore
    val fruitServeSize: String, // Serving size, Fruitservesize
    val fruitVariety: String // Variety Fruitvariationsscore
)
