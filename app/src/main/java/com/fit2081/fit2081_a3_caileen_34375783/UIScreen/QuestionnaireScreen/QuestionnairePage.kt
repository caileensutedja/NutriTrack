package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.QuestionnaireScreen

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen.HomeViewModel
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.InsightScreen.InsightsPage
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.LoginScreen.LoginPage
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class QuestionnairePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
                        QuestionnaireScreen()
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
fun QuestionnaireScreen(){
    val questionnaireViewModel: QuestionnaireViewModel = viewModel()
    val mContext = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    // Needed to show the dialogs.
    var mTimeMealPickerDialog = timePickerFun(questionnaireViewModel.timeMeal)
    var mTimeSleepPickerDialog = timePickerFun(questionnaireViewModel.timeSleep)
    var mTimeWakeUpPickerDialog = timePickerFun(questionnaireViewModel.timeWakeUp)
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
                questionnaireViewModel.categoryByColumn.forEach { catColumn ->
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
            questionnaireViewModel.personaList.chunked(3).forEach { rowItems ->
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
            value = questionnaireViewModel.persona.value,
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
            questionnaireViewModel.personaList.forEach { persona ->
                DropdownMenuItem(
                    text = { Text(persona.name) },
                    onClick = {
                        questionnaireViewModel.persona.value = persona.name
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
                //Meal
                Button(onClick = { mTimeMealPickerDialog.show()}) {
                    Text("Pick Time", fontSize = 10.sp)
                }
                Text(text = "Selected time: ${questionnaireViewModel.timeMeal.value}", fontSize = 7.sp,
                    modifier = Modifier.padding(1.dp))
                //Sleep
                Button(onClick = {
                    mTimeSleepPickerDialog.show()
                }
                ) {
                    Text("Pick Time", fontSize = 10.sp)
                }
                //Wakeup
                Text(text = "Selected time: ${questionnaireViewModel.timeSleep.value}", fontSize = 7.sp)
                Button(onClick = { mTimeWakeUpPickerDialog.show() }) {
                    Text("Pick Time", fontSize = 10.sp)
                }
                Text(text = "Selected time: ${questionnaireViewModel.timeWakeUp.value}", fontSize = 7.sp)
            }
        }
        // Save Button
        Row {
            Button(onClick = {
                if(questionnaireViewModel.submit()) {
                    // Only go next if it is successful.
                    mContext.startActivity(Intent(mContext, InsightsPage::class.java))
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
            mTime.value = String.format("%02d:%02d", mHour, mMinute)
        // To user 24h format or not
        }, mHour, mMinute, false
    )
}