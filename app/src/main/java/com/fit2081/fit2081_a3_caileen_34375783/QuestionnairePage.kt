package com.fit2081.fit2081_a3_caileen_34375783

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme
import java.util.Calendar

class QuestionnairePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            FIT2081_A3_Caileen_34375783Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //losadfromsg
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopAppBar()
                        QuestionnaireScreen()
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

@Preview(showBackground = true)
@Composable
fun QuestionnaireScreen(){
    val mContext = LocalContext.current

    val mCheckBoxFruits = remember { mutableStateOf(false) }
    val mCheckBoxVegetables = remember { mutableStateOf(false) }
    val mCheckBoxGrains = remember { mutableStateOf(false) }
    val mCheckBoxRedMeat = remember { mutableStateOf(false) }
    val mCheckBoxSeafood = remember { mutableStateOf(false) }
    val mCheckBoxPoultry = remember { mutableStateOf(false) }
    val mCheckBoxFish = remember { mutableStateOf(false) }
    val mCheckBoxEggs = remember { mutableStateOf(false) }
    val mCheckBoxNutsSeeds = remember { mutableStateOf(false) }

    // For Persona Choice
    val personaChoices = listOf("Health Devotee", "Mindful Eater", "Wellness Striver", "Balance Seeker", "Health Procrastinator", "Food Carefree")
    var expanded by remember { mutableStateOf(false) }
    var mSelectedPersona by remember { mutableStateOf("") }

    // For Time Pickers
    val mTimeMeal = remember { mutableStateOf("") }
    val mTimeSleep = remember { mutableStateOf("") }
    val mTimeWakeUp = remember { mutableStateOf("") }

    val mTimeMealPickerDialog = timePickerFun(mTimeMeal)
    val mTimeSleepPickerDialog = timePickerFun(mTimeSleep)
    val mTimeWakeUpPickerDialog = timePickerFun(mTimeWakeUp)

    // Use LaunchedEffect to restore once.
    // Runs the first time the function runs, not at every change (as it is composable)
    LaunchedEffect (Unit) {
        val sharedPref = mContext.getSharedPreferences("Assignment1", Context.MODE_PRIVATE)

        mCheckBoxFruits.value = sharedPref.getBoolean("fruits", false)
        mCheckBoxVegetables.value = sharedPref.getBoolean("vegetables", false)
        mCheckBoxGrains.value = sharedPref.getBoolean("grains", false)
        mCheckBoxRedMeat.value = sharedPref.getBoolean("redMeat", false)
        mCheckBoxSeafood.value = sharedPref.getBoolean("seafood", false)
        mCheckBoxPoultry.value = sharedPref.getBoolean("poultry", false)
        mCheckBoxFish.value = sharedPref.getBoolean("fish", false)
        mCheckBoxEggs.value = sharedPref.getBoolean("eggs", false)
        mCheckBoxNutsSeeds.value = sharedPref.getBoolean("nutsSeeds", false)
        mCheckBoxVegetables.value = sharedPref.getBoolean("vegetables", false)

        val loadedPersona = sharedPref.getString("persona", "")
        val loadedTimeMeal = sharedPref.getString("timeMeal", "")
        val loadedTimeSleep = sharedPref.getString("timeSleep", "")
        val loadedTimeWakeUp = sharedPref.getString("timeWakeUp", "")
        mSelectedPersona = loadedPersona.toString()
        mTimeMeal.value = loadedTimeMeal.toString()
        mTimeSleep.value = loadedTimeSleep.toString()
        mTimeWakeUp.value = loadedTimeWakeUp.toString()
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // Column 1: Fruits, Red Meat, Fish
                Column(horizontalAlignment = Alignment.Start) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = mCheckBoxFruits.value,
                            onCheckedChange = { mCheckBoxFruits.value = it })
                        Text(text = "Fruits")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = mCheckBoxRedMeat.value,
                            onCheckedChange = { mCheckBoxRedMeat.value = it }
                        )
                        Text(text = "Red Meat")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = mCheckBoxFish.value,
                            onCheckedChange = { mCheckBoxFish.value = it }
                        )
                        Text(text = "Fish")
                    }
                }
                // Column 2: Vegetables, Seafood, Eggs
                Column(horizontalAlignment = Alignment.Start) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = mCheckBoxVegetables.value,
                            onCheckedChange = { mCheckBoxVegetables.value = it })
                        Text(text = "Vegetables")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = mCheckBoxSeafood.value,
                            onCheckedChange = { mCheckBoxSeafood.value = it })
                        Text(text = "Seafood")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = mCheckBoxEggs.value,
                            onCheckedChange = { mCheckBoxEggs.value = it })
                        Text(text = "Eggs")
                    }
                }
                // Column 3: Grains, Poultry, Nuts/Seeds
                Column(horizontalAlignment = Alignment.Start) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = mCheckBoxGrains.value,
                            onCheckedChange = { mCheckBoxGrains.value = it })
                        Text(text = "Grains")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = mCheckBoxPoultry.value,
                            onCheckedChange = { mCheckBoxPoultry.value = it })
                        Text(text = "Poultry")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = mCheckBoxNutsSeeds.value,
                            onCheckedChange = { mCheckBoxNutsSeeds.value = it })
                        Text(text = "Nuts/Seeds")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(4.dp))
        //Persona
        Text(
            text = "Your Persona",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 15.sp
        )
        Text(
            text = "People can be broadly classified into 6 different types based on their eating preferences. Click on each button below to find out the different types, and select the type that best fits you!",
            fontSize = 10.sp,
            lineHeight = 12.sp
        )
        // Persona Information Modals
        Persona()
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
//                if (mSelectedPersona.isNotEmpty() && mTimeMeal.value.isNotEmpty() && mTimeSleep.value.isNotEmpty() && mTimeWakeUp.value.isNotEmpty()){
//                    val sharedPref = mContext.getSharedPreferences("Assignment1", Context.MODE_PRIVATE).edit()
//
//                    sharedPref.putBoolean("fruits", mCheckBoxFruits.value)
//                    sharedPref.putBoolean("vegetables", mCheckBoxVegetables.value)
//                    sharedPref.putBoolean("grains", mCheckBoxGrains.value)
//                    sharedPref.putBoolean("redMeat", mCheckBoxRedMeat.value)
//                    sharedPref.putBoolean("seafood", mCheckBoxSeafood.value)
//                    sharedPref.putBoolean("poultry", mCheckBoxPoultry.value)
//                    sharedPref.putBoolean("fish", mCheckBoxFish.value)
//                    sharedPref.putBoolean("eggs", mCheckBoxEggs.value)
//                    sharedPref.putBoolean("nutsSeeds", mCheckBoxNutsSeeds.value)
//                    sharedPref.putString("persona", mSelectedPersona)
//                    sharedPref.putString("timeMeal", mTimeMeal.value)
//                    sharedPref.putString("timeSleep", mTimeSleep.value)
//                    sharedPref.putString("timeWakeUp", mTimeWakeUp.value)

//                    sharedPref.apply()

//                    mContext.startActivity(Intent(mContext, HomeScreen::class.java))

                        mContext.startActivity(Intent(mContext, test::class.java))
//            } else {
//                    // Error message to fill in the questionnaire.
//                    Toast.makeText(mContext, "Please complete all parts of the questionnaire.", Toast.LENGTH_LONG).show()
//
//                }
            }){
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun Persona(){
    var showDialogHealthDevotee by remember { mutableStateOf(false) }
    var showDialogMindfulEater by remember { mutableStateOf(false) }
    var showDialogWellnessStriver by remember { mutableStateOf(false) }
    var showDialogBalanceSeeker by remember { mutableStateOf(false) }
    var showDialogHealthProcrastinator by remember { mutableStateOf(false) }
    var showDialogFoodCarefree by remember { mutableStateOf(false) }
    Row {
        Button(onClick = {showDialogHealthDevotee = true}) { Text("Health Devotee", fontSize = 10.sp) }
        if (showDialogHealthDevotee){
            AlertDialog(
                onDismissRequest = {showDialogHealthDevotee = false},
                confirmButton = {},
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Image(painter = painterResource(id = R.drawable.persona_1),
                            contentDescription = "Persona 1",
                            modifier = Modifier.size(180.dp),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Health Devotee",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.",
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = {showDialogHealthDevotee = false}) {
                            Text("Dismiss") }
                    }

               }
            )
        }
        Button(onClick = {showDialogMindfulEater = true}) { Text("Mindful Eater", fontSize = 10.sp) }
        if (showDialogMindfulEater){
            AlertDialog(
                onDismissRequest = {showDialogMindfulEater = false},
                confirmButton = {},
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Image(painter = painterResource(id = R.drawable.persona_2),
                            contentDescription = "Persona 2",
                            modifier = Modifier.size(180.dp),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Mindful Eater",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.",
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = {showDialogMindfulEater = false}) {
                            Text("Dismiss") }
                    }
                }
            )
        }
        Button(onClick = {showDialogWellnessStriver = true}) { Text("Wellness Striver", fontSize = 10.sp) }
        if (showDialogWellnessStriver){
            AlertDialog(
                onDismissRequest = {showDialogWellnessStriver = false},
                confirmButton = {},
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Image(painter = painterResource(id = R.drawable.persona_3),
                            contentDescription = "Persona 3",
                            modifier = Modifier.size(180.dp),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Wellness Striver",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.",
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = {showDialogWellnessStriver = false},
                        ) {
                            Text("Dismiss") }
                    }
                }
            )
        }
    }
    Row {
        Button(onClick = {showDialogBalanceSeeker = true}) { Text("Balance Seeker", fontSize = 10.sp) }
        if (showDialogBalanceSeeker){
            AlertDialog(
                onDismissRequest = {showDialogBalanceSeeker = false},
                confirmButton = {},
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Image(painter = painterResource(id = R.drawable.persona_4),
                            contentDescription = "Persona 4",
                            modifier = Modifier.size(180.dp),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Balance Seeker",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.",
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = {showDialogBalanceSeeker = false}) {
                            Text("Dismiss") }
                    }
                }
            )
        }
        Button(onClick = {showDialogHealthProcrastinator = true}) { Text("Health Procrastinator", fontSize = 10.sp) }
        if (showDialogHealthProcrastinator){
            AlertDialog(
                onDismissRequest = {showDialogHealthProcrastinator = false},
                confirmButton = {},
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Image(painter = painterResource(id = R.drawable.persona_5),
                            contentDescription = "Persona 5",
                            modifier = Modifier.size(180.dp),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Health Procrastinator",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.",
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = {showDialogHealthProcrastinator = false}) {
                            Text("Dismiss") }
                    }
                }
            )
        }
        Button(onClick = {showDialogFoodCarefree = true}) { Text("Food Carefree", fontSize = 10.sp) }
        if (showDialogFoodCarefree){
            AlertDialog(
                onDismissRequest = {showDialogFoodCarefree = false},
                confirmButton = {},
                text = {
                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Image(painter = painterResource(id = R.drawable.persona_6),
                            contentDescription = "Persona 6",
                            modifier = Modifier.size(180.dp),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Food Carefree",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.",
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = {showDialogFoodCarefree = false}) {
                            Text("Dismiss") }
                    }
                }
            )
        }
    }
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