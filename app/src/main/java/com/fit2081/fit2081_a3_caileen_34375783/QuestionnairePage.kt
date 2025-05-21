package com.fit2081.fit2081_a3_caileen_34375783

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.fit2081_a3_caileen_34375783.FoodIntake.FoodIntake
import com.fit2081.fit2081_a3_caileen_34375783.FoodIntake.FoodIntakeViewModel
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientViewModel
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class QuestionnairePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val foodIntakeViewModel: FoodIntakeViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            FIT2081_A3_Caileen_34375783Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopAppBar()
                        Log.d("CRASH ISSUE", "passes before qscreen")
                        QuestionnaireScreen(foodIntakeViewModel)
                        Log.d("CRASH ISSUE", "passes after qscreen")
                    }
                }
            }
        }
    }
}

/**
 * Top app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text( text = "Food Intake Questionnaire",
                fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(onClick = {
                context.startActivity(Intent(context, LoginPage::class.java))
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

data class FoodCheckboxItem(
    val label: String,
    val state: MutableState<Boolean>
)

@Composable
fun LabeledCheckbox(item: FoodCheckboxItem) {
    Row(verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.state.value,
            onCheckedChange = { item.state.value = it },
        )
        Text(text = item.label)
    }
}
//@Preview(showBackground = true)
@Composable
fun QuestionnaireScreen(foodIntakeViewModel: FoodIntakeViewModel){
    val mID = AuthManager.getPatientId().toString()
    val foodIntakeAttempt by foodIntakeViewModel.getQuizAttemptByPatientId(mID).collectAsStateWithLifecycle(null)

    Log.d("CRASH ISSUE", "in qscreen")
    val mContext = LocalContext.current

    var mCheckBoxFruits = remember { mutableStateOf(false) }
    var mCheckBoxVegetables = remember { mutableStateOf(false) }
    var mCheckBoxGrains = remember { mutableStateOf(false) }
    var mCheckBoxRedMeat = remember { mutableStateOf(false) }
    var mCheckBoxSeafood = remember { mutableStateOf(false) }
    var mCheckBoxPoultry = remember { mutableStateOf(false) }
    var mCheckBoxFish = remember { mutableStateOf(false) }
    var mCheckBoxEggs = remember { mutableStateOf(false) }
    var mCheckBoxNutsSeeds = remember { mutableStateOf(false) }

    // For Persona Choice
    val personaChoices = listOf("Health Devotee", "Mindful Eater", "Wellness Striver", "Balance Seeker", "Health Procrastinator", "Food Carefree")
    var expanded by remember { mutableStateOf(false) }
    var mSelectedPersona by remember { mutableStateOf("") }

    // For Time Pickers
    var mTimeMeal = remember { mutableStateOf("") }
    var mTimeSleep = remember { mutableStateOf("") }
    var mTimeWakeUp = remember { mutableStateOf("") }

    var mTimeMealPickerDialog = timePickerFun(mTimeMeal)
    var mTimeSleepPickerDialog = timePickerFun(mTimeSleep)
    var mTimeWakeUpPickerDialog = timePickerFun(mTimeWakeUp)

    val categoryByColumn = listOf(
        listOf(
            FoodCheckboxItem("Fruits", mCheckBoxFruits),
            FoodCheckboxItem("Vegetables", mCheckBoxVegetables),
            FoodCheckboxItem("Grains", mCheckBoxGrains)
        ),
        listOf(
            FoodCheckboxItem("Red Meat", mCheckBoxRedMeat),
            FoodCheckboxItem("Seafood", mCheckBoxSeafood),
            FoodCheckboxItem("Poultry", mCheckBoxPoultry)
        ),
        listOf(
            FoodCheckboxItem("Fish", mCheckBoxFish),
            FoodCheckboxItem("Eggs", mCheckBoxEggs),
            FoodCheckboxItem("Nuts/Seeds", mCheckBoxNutsSeeds)
        )
    )

    val personaList = listOf(
        PersonaChoice(
            "Health Devotee",
            "I’m passionate about healthy eating & health plays a big part in my life...",
            R.drawable.persona_1
        ),
        PersonaChoice(
            "Mindful Eater",
            "I’m health-conscious and being healthy and eating healthy is important to me...",
            R.drawable.persona_2
        ),
        PersonaChoice(
            "Wellness Striver",
            "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work...",
            R.drawable.persona_3
        ),
        PersonaChoice(
            "Balance Seeker",
            "I try and live a balanced lifestyle, and I think that all foods are okay in moderation...",
            R.drawable.persona_4
        ),
        PersonaChoice(
            "Health Procrastinator",
            "I’m contemplating healthy eating but it’s not a priority for me right now...",
            R.drawable.persona_5
        ),
        PersonaChoice(
            "Food Carefree",
            "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it...",
            R.drawable.persona_6
        )
    )

//    validateTime("08:18", "22:00", "15.30")
//    Log.d("time vali", "time vali" +     validateTime("08:18", "22:00", "15.30")
    LaunchedEffect(foodIntakeAttempt) {
        if (foodIntakeAttempt != null){
            mCheckBoxFruits.value = foodIntakeAttempt?.fruit == true
            mCheckBoxVegetables.value = foodIntakeAttempt?.vegetable == true
            mCheckBoxGrains.value = foodIntakeAttempt?.grains == true
            mCheckBoxRedMeat.value = foodIntakeAttempt?.redMeat == true
            mCheckBoxSeafood.value = foodIntakeAttempt?.seafood == true
            mCheckBoxPoultry.value = foodIntakeAttempt?.poultry == true
            mCheckBoxFish.value = foodIntakeAttempt?.fish == true
            mCheckBoxEggs.value = foodIntakeAttempt?.eggs == true
            mCheckBoxNutsSeeds.value = foodIntakeAttempt?.nutsSeeds == true
            mSelectedPersona = foodIntakeAttempt?.persona.toString()
            mTimeMeal.value = foodIntakeAttempt?.timeMeal.toString()
            mTimeSleep.value = foodIntakeAttempt?.timeSleep.toString()
            mTimeWakeUp.value = foodIntakeAttempt?.timeWakeup.toString()

            //to next screen, alr have attempt
            Log.d("FOOD INTAKE ATTEMPT", "FOOD INTAKE ATTEMPT - HAS ATTEMPT" + foodIntakeAttempt?.fruit)

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()), // ADDED AFTER ASSIGNMENT SUBMISSION - TO SCROLL!!!!!!!
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tick all the food categories you can eat",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        //Check boxes
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            /**
             * Food Category Checkboxes
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 1.dp, end = 1.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                categoryByColumn.forEach { catColumn ->
                    Column(horizontalAlignment = Alignment.Start) {
                        catColumn.forEach { item ->
                            LabeledCheckbox(item)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))

        /**
         * Persona Selection Section
         */
        Text(
            text = "Your Persona",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 15.sp
        )
        Text(
            text = "People can be broadly classified into 6 different types based on their eating preferences. Click on each button below to find out the different types, and select the type that best fits you!",
            fontSize = 10.sp,
            lineHeight = 12.sp,
            textAlign = TextAlign.Center
        )
        // Persona Information Modals
        var selectedPersonaModal by remember { mutableStateOf<PersonaChoice?>(null) }
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            personaList.chunked(3).forEach { rowItems ->
                Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    rowItems.forEach { persona ->
                        Button(
                            onClick = { selectedPersonaModal = persona },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(persona.name, fontSize = 9.5.sp)
                        }
                    }
                }
            }
            // Show dialog when a persona option is selected by clicking the button
            selectedPersonaModal?.let { persona ->
                PersonaModal(persona = persona, onDismiss = { selectedPersonaModal = null })
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))
        HorizontalDivider()
        // Persona Choice
        Text(
            text = "Which persona best fits you?",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 13.sp
        )
        OutlinedTextField(
            value = mSelectedPersona,
            onValueChange = {},
            label = { Text(text = "Select option") },
            modifier = Modifier.fillMaxWidth().padding(2.dp),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            // Prevents typing in the input
            readOnly = true
        )
        //Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            personaChoices.forEach { persona ->
                DropdownMenuItem(
                    text = { Text(persona) },
                    onClick = {
                        mSelectedPersona = persona
                        expanded = false
                    }
                )
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        // Timings
        Text(
            text = "Timings",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 15.sp
        )
        // Time Pickers
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(0.65f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "What time of day approx. do you normally eat your biggest meal?",
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    "What time of day approx. do you go to sleep at night?",
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    "What time of day approx. do you wake up in the morning?",
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.dp)

            ) {
                Button(onClick = { mTimeMealPickerDialog.show()}) {
                    Text("Pick Time", fontSize = 10.sp)
                }
                Text(text = "Selected time: ${mTimeMeal.value}", fontSize = 7.sp,
                    modifier = Modifier.padding(1.dp))
                Button(onClick = { mTimeSleepPickerDialog.show() }) {
                    Text("Pick Time", fontSize = 10.sp)
                }
                Text(text = "Selected time: ${mTimeSleep.value}", fontSize = 7.sp)
                Button(onClick = { mTimeWakeUpPickerDialog.show() }) {
                    Text("Pick Time", fontSize = 10.sp)
                }
                Text(text = "Selected time: ${mTimeWakeUp.value}", fontSize = 7.sp)
            }
        }
        // Save Button
        Row {
            Button(onClick = {
                // Validate: No empty Strings
                if (mSelectedPersona.isNotEmpty() && mTimeMeal.value.isNotEmpty() && mTimeSleep.value.isNotEmpty() && mTimeWakeUp.value.isNotEmpty()){
                    // Validate: TimePickers reasonable time. Eat should be after waking up and before sleeping.
//                    if (validateTime(mTimeWakeUp.value, mTimeSleep.value, mTimeMeal.value)) {
                    var foodIntakeAnswer: FoodIntake = FoodIntake(
                        userID = AuthManager.getPatientId().toString(),
                        fruit =  mCheckBoxFruits.value,
                        vegetable =  mCheckBoxVegetables.value,
                        grains =  mCheckBoxGrains.value,
                        redMeat = mCheckBoxRedMeat.value,
                        seafood = mCheckBoxSeafood.value,
                        poultry = mCheckBoxPoultry.value,
                        fish = mCheckBoxFish.value,
                        eggs = mCheckBoxEggs.value,
                        nutsSeeds = mCheckBoxNutsSeeds.value,
                        persona = mSelectedPersona,
                        timeMeal = mTimeMeal.value,
                        timeSleep = mTimeSleep.value,
                        timeWakeup = mTimeWakeUp.value
                    )
                    foodIntakeViewModel.attemptFoodIntake(foodIntakeAnswer)
//                    if (foodIntakeAttempt != null) {
//
//                    }
//                        CoroutineScope(Dispatchers.IO).launch {
//                            foodIntakeViewModel.attemptFoodIntake(foodIntakeAnswer)
//                        }

                        Log.d("debug questionnaire", "before")

                        mContext.startActivity(Intent(mContext, InsightsPage::class.java))
                        Log.d("debug questionnaire", "after")
//                    } else{
//                        Toast.makeText(mContext, "Please choose reasonable timings, where meals are after waking up and before sleeping.", Toast.LENGTH_LONG).show()
//
//                    }
            } else {
                    // Error message to fill in the questionnaire.
                    Toast.makeText(mContext, "Please complete all parts of the questionnaire.", Toast.LENGTH_LONG).show()

                }
            }){
                Text(text = "Save")
            }
        }
    }
}

data class PersonaChoice(
    val name: String,
    val description: String,
    val imageResId: Int
)

@Composable
fun PersonaModal(persona: PersonaChoice, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = persona.imageResId),
                    contentDescription = persona.name,
                    modifier = Modifier.size(180.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = persona.name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = persona.description,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = onDismiss) {
                    Text("Dismiss")
                }
            }
        }
    )
}

/**
 * The function timePickerFun was taken from FIT2081's Week 3.3 Lab.
 */
@Composable
fun timePickerFun(mTime: MutableState<String>): TimePickerDialog {
    // Get the current context
    val mContext = LocalContext.current
    // Get a calendar instance
    val mCalendar = Calendar.getInstance()

    // Get the current hour and minute
    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    // Set the calendar's time to the current time
    mCalendar.time = Calendar.getInstance().time
    // Return a TimePickerDialog
    return TimePickerDialog(
        mContext,
        // Invoke listener when time is set
        { _, mHour: Int, mMinute: Int ->
            // initial hour and minute
            mTime.value = "$mHour:$mMinute"
        // To user 24h format or not
        }, mHour, mMinute, false
    )
}

//fun validateTime(wakeup, sleep, meal) {
////    fun validateTimes(sleepTimeStr: String, wakeTimeStr: String, eatTimeStr: String): Boolean {
//        val sleepTime = parseTime(sleep)
//        val wakeTime = parseTime(wakeup)
//        val eatTime = parseTime(meal)
//
//        if (sleepTime == wakeTime) return false
//
//        // Handle "sleep after midnight" case:
//        val adjustedSleepTime = if (sleepTime.isBefore(wakeTime)) sleepTime.plusHours(24) else sleepTime
//        val adjustedEatTime = if (eatTime.isBefore(wakeTime)) eatTime.plusHours(24) else eatTime
//
//        return eatTime.isAfter(wakeTime) && adjustedEatTime.isBefore(adjustedSleepTime)
//    }

fun validateTime(sleepTimeStr: String, wakeTimeStr: String, eatTimeStr: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val sleepTime = LocalTime.parse(sleepTimeStr, formatter)
    val wakeTime = LocalTime.parse(wakeTimeStr, formatter)
    val eatTime = LocalTime.parse(eatTimeStr, formatter)
    Log.d("TIMEVALI", "TIMEVALI sleep time is: " + sleepTime)
    Log.d("TIMEVALI", "TIMEVALI wake time is: " + wakeTime)
    Log.d("TIMEVALI", "TIMEVALI eat time is: " + eatTime)


    // Is not possible to do this
    if (sleepTime == wakeTime) return false

    return if (sleepTime.isBefore(wakeTime)) {
        Log.d("TIMEVALI", "TIMEVALI sleep is before wakeup - midnight sleep am")
        Log.d("TIMEVALI", "TIMEVALI sleep is before wakeup - eatTime.isBefore(sleepTime): " + eatTime.isBefore(sleepTime))
        Log.d("TIMEVALI", "TIMEVALI sleep is before wakeup - eatTime.isAfter(wakeTime) " + eatTime.isAfter(wakeTime))
        // Sleep and wake on the same day - sleeps on the am
        eatTime.isBefore(sleepTime) || eatTime.isAfter(wakeTime)
    } else {
        Log.d("TIMEVALI", "TIMEVALI sleep time is after wakeup - normal time")
        Log.d("TIMEVALI", "TIMEVALI sleep time is after wakeup - eatTime.isAfter(wakeTime): " + eatTime.isAfter(wakeTime))
        Log.d("TIMEVALI", "TIMEVALI sleep time is after wakeup - eatTime.isBefore(sleepTime): " + eatTime.isBefore(sleepTime))

        // Sleep through midnight
        eatTime.isAfter(wakeTime) && eatTime.isBefore(sleepTime).not()
    }
}
