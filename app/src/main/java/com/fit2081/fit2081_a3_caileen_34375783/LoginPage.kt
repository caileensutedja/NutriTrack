package com.fit2081.fit2081_a3_caileen_34375783

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A1_Caileen_34375783Theme
import java.io.BufferedReader
import java.io.InputStreamReader

class LoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FIT2081_A1_Caileen_34375783Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    //Variables
    var userId by remember { mutableStateOf("") }
    var userPhone by remember { mutableStateOf("") }
    var phoneNoError by remember { mutableStateOf(false) }
    var idList by remember { mutableStateOf(listOf<String>()) }

    // Boolean where true is showing the DropdownMenu and false is closing it.
    var expanded by remember { mutableStateOf(false) }
    val mContext = LocalContext.current

    // Is ran once.
    LaunchedEffect(Unit) {
        idList = readIDFromCSV(mContext, "data.csv")
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Log In",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(30.dp))
            // ID
            OutlinedTextField(
                value = userId,
                onValueChange = {},
                label = { Text(text = "My ID (Provided by your Clinician)") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {expanded = true}) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                },
                // Prevents typing in the input
                readOnly = true
            )
            //Dropdown menu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false},
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                idList.forEach { id ->
                    DropdownMenuItem(
                        text = {Text(id)},
                        onClick = { userId = id
                        expanded = false
                    })
                }
            }
            //ID Validation
            if (userId.isEmpty()) {
                Text(
                    text = "Please select your registered ID",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Phone number
            OutlinedTextField(
                value = userPhone,
                onValueChange = {userPhone = it
                                phoneNoError = it.length == 11},
                label = { Text(text = "Phone number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // Phone number input validation.
            if (!phoneNoError) {
                Text(
                    text = "Phone number must be 11 characters.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = "This app is only for pre-registered users. Please have your ID and phone number handy before continuing.",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (userId.isNotEmpty() && phoneNoError) {
                        if (validateLogin(mContext, "data.csv", userId, userPhone)) {
                            // Success message
                            Toast.makeText(mContext, "Login Successful", Toast.LENGTH_LONG).show()

                            val sharedPrefGet = mContext.getSharedPreferences(
                                "Assignment1",
                                Context.MODE_PRIVATE
                            )
                            val idStored = sharedPrefGet.getString("id", " ")

                            if (idStored == userId) {
                                mContext.startActivity(Intent(mContext, HomeScreen::class.java))
                            } else {
                                // Store the ID at shared preference
                                val sharedPrefPut = mContext.getSharedPreferences(
                                    "Assignment1",
                                    Context.MODE_PRIVATE
                                ).edit()
                                // Set initial values (to be default)
                                sharedPrefPut.putString("id", userId)
                                sharedPrefPut.putBoolean("fruits", false)
                                sharedPrefPut.putBoolean("vegetables", false)
                                sharedPrefPut.putBoolean("grains", false)
                                sharedPrefPut.putBoolean("redMeat", false)
                                sharedPrefPut.putBoolean("seafood", false)
                                sharedPrefPut.putBoolean("poultry", false)
                                sharedPrefPut.putBoolean("fish", false)
                                sharedPrefPut.putBoolean("eggs", false)
                                sharedPrefPut.putBoolean("nutsSeeds", false)
                                sharedPrefPut.putString("persona", "")
                                sharedPrefPut.putString("timeMeal", "")
                                sharedPrefPut.putString("timeSleep", "")
                                sharedPrefPut.putString("timeWakeUp", "")
                                sharedPrefPut.apply()
                                // Move to the next page
                                mContext.startActivity(
                                    Intent(
                                        mContext,
                                        QuestionnairePage::class.java
                                    )
                                )
                            }
                        } else {
                            // Error message
                            Toast.makeText(mContext, "Incorrect Credentials", Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        // Error message
                        Toast.makeText(mContext, "Please fill in your credentials.", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text("Continue")
                    }
        }
    }
}

/**
 * Function for to validate the login attempt.
 * It checks if the ID matches the appropriate phone number.
 */
fun validateLogin(context: Context, fileName: String, userId: String, userPhone: String): Boolean {
    val assets = context.assets
    var isValidUser = false // Initialise to false

    try {
        val inputStream = assets.open(fileName)

        val reader = BufferedReader(InputStreamReader(inputStream))
        //Read CSV by line
        reader.useLines { lines ->
            // Drop the header
            lines.drop(1).forEach { line ->
                // Read data per line separated by comma
                val values = line.split(",")
                if (values.size > 1 && values[0] == userPhone && values[1] == userId ){
                    isValidUser = true
                    return@forEach
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return isValidUser
}

/**
 * Function for the dropdown menu.
 */
fun readIDFromCSV(context: Context, fileName: String): List<String> {
    val ids = mutableListOf<String>()
    val assets = context.assets

    try {
        val inputStream = assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        //Read CSV by line
        reader.useLines { lines ->
            // Drop the header
            lines.drop(1).forEach { line ->
                // Read data per line separated by comma
                val values = line.split(",")
                if (values.size > 1) {
                    val id = values[1]
                    ids.add(id)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ids
}