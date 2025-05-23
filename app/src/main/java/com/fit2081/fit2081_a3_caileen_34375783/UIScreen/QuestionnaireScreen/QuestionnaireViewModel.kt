package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.QuestionnaireScreen

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.FoodIntake.FoodIntake
import com.fit2081.fit2081_a3_caileen_34375783.FoodIntake.FoodIntakeRepository
import com.fit2081.fit2081_a3_caileen_34375783.R
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter


/**
 * ViewModel class for managing food intake attempts data.
 * This class interacts with the FoodIntake Repository to perform database operations
 * and provides data to the UI.
 *
 * @param context The application context.
 */
class QuestionnaireViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FoodIntakeRepository(application.applicationContext)

    var userId = mutableStateOf<String?>(null)

    var fruit = mutableStateOf(false)
    var vegetable = mutableStateOf(false)
    var grains = mutableStateOf(false)
    var redMeat = mutableStateOf(false)
    var seafood = mutableStateOf(false)
    var poultry = mutableStateOf(false)
    var fish = mutableStateOf(false)
    var eggs = mutableStateOf(false)
    var nutsSeeds = mutableStateOf(false)

    var persona = mutableStateOf<String>("")
    var timeMeal = mutableStateOf<String>("00:00")
    var timeSleep = mutableStateOf<String>("00:00")
    var timeWakeUp = mutableStateOf<String>("00:00")

    var categoryByColumn = listOf(
        listOf(
            FoodCheckboxItem("Fruits", fruit),
            FoodCheckboxItem("Vegetables", vegetable),
            FoodCheckboxItem("Grains", grains)
        ),
        listOf(
            FoodCheckboxItem("Red Meat", redMeat),
            FoodCheckboxItem("Seafood", seafood),
            FoodCheckboxItem("Poultry", poultry)
        ),
        listOf(
            FoodCheckboxItem("Fish", fish),
            FoodCheckboxItem("Eggs", eggs),
            FoodCheckboxItem("Nuts/Seeds", nutsSeeds)
        )
    )

    val personaList = listOf(
        PersonaChoice(
            "Health Devotee",
            "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.",
            R.drawable.persona_1
        ),
        PersonaChoice(
            "Mindful Eater",
    "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.",
            R.drawable.persona_2
        ),
        PersonaChoice(
            "Wellness Striver",
            "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.",
            R.drawable.persona_3
        ),
        PersonaChoice(
            "Balance Seeker",
            "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.",
            R.drawable.persona_4
        ),
        PersonaChoice(
            "Health Procrastinator",
            "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.",
            R.drawable.persona_5
        ),
        PersonaChoice(
            "Food Carefree",
            "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.",
            R.drawable.persona_6
        )
    )

    init {
        val patientId = AuthManager.getPatientId().toString()
        userId.value = patientId
        getQuizAttemptByPatientId(patientId)
    }

    // Fetch quiz attempt for patient ID and update UI state
    fun getQuizAttemptByPatientId(patientId: String) {
        viewModelScope.launch {
            repository.getQuizAttemptByPatientId(patientId)
                .collect { foodIntakeData ->
                    if (foodIntakeData != null) {
                        fruit.value = foodIntakeData.fruit
                        vegetable.value = foodIntakeData.vegetable
                        grains.value = foodIntakeData.grains
                        redMeat.value = foodIntakeData.redMeat
                        seafood.value = foodIntakeData.seafood
                        poultry.value = foodIntakeData.poultry
                        fish.value = foodIntakeData.fish
                        eggs.value = foodIntakeData.eggs
                        nutsSeeds.value = foodIntakeData.nutsSeeds
                        persona.value = foodIntakeData.persona
                        timeMeal.value = foodIntakeData.timeMeal
                        timeSleep.value = foodIntakeData.timeSleep
                        timeWakeUp.value = foodIntakeData.timeWakeup
                    }
                }
        }
    }

    // Submit the questionnaire
    fun submit() : Boolean{
        Log.d("debug questionnaire", "debug questionnaire time meal is now: " + timeMeal.value)
        val foodIntake = FoodIntake(
            userID = userId.value ?: "",
            fruit = fruit.value,
            vegetable = vegetable.value,
            grains = grains.value,
            redMeat = redMeat.value,
            seafood = seafood.value,
            poultry = poultry.value,
            fish = fish.value,
            eggs = eggs.value,
            nutsSeeds = nutsSeeds.value,
            persona = persona.value ?: "",
            timeMeal = timeMeal.value,
            timeSleep = timeSleep.value,
            timeWakeup = timeWakeUp.value
        )
//        Log.d("debug questionnaire", "debug questionnaire time meal is now: " + timeMeal.value)
        Log.d("debug questionnaire", "debug questionnaire sleep meal is now: " + timeSleep.value)
        Log.d("debug questionnaire", "debug questionnaire wake meal is now: " + timeWakeUp.value)

        if (timeSleep.value != "00:00" && timeWakeUp.value!= "00:00" && timeMeal.value!= "00:00" && persona.value != "") {
            if (validateTime(timeSleep.value, timeWakeUp.value, timeMeal.value) ){
                    viewModelScope.launch {
                        repository.attemptFoodIntake(foodIntake)
                    }
                    Toast.makeText(application, "Questionnaire saved!.", Toast.LENGTH_SHORT).show()
                    return true
            } else {
                Toast.makeText(application, "Please choose reasonable timings.", Toast.LENGTH_SHORT).show()
                return false
                }
            } else {
                Toast.makeText(application, "Please fill in all parts of the questionnaire.", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun validateTime(sleep: String, wake: String, eat: String): Boolean {
        return try {
            val fmt = DateTimeFormatter.ofPattern("HH:mm")
            val sleepTime = LocalTime.parse(sleep, fmt)
            val wakeTime = LocalTime.parse(wake, fmt)
            val eatTime = LocalTime.parse(eat, fmt)

            if (sleepTime == wakeTime || sleepTime == eatTime || wakeTime == eatTime) return false

            if (sleepTime.isBefore(wakeTime)) {
                eatTime.isBefore(sleepTime) || eatTime.isAfter(wakeTime)
            } else {
                eatTime.isAfter(wakeTime) && eatTime.isBefore(sleepTime)
            }
        } catch (e: Exception) {
            false
        }
    }
}