package com.fit2081.fit2081_a3_caileen_34375783

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.fit2081.fit2081_a3_caileen_34375783.patient.Patient
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientViewModel
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme
import java.io.BufferedReader
import java.io.InputStreamReader
import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation


class LoginPage : ComponentActivity() {
    // Patient View Model
    val patientViewModel: PatientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showLoginScreen by remember {mutableStateOf(true)}
            FIT2081_A3_Caileen_34375783Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showLoginScreen) {
                      LoginScreen(
                          modifier = Modifier.padding(innerPadding),
                          patientViewModel,
                          goToRegisterScreen = { showLoginScreen = false}
                      )
                    } else {
                        RegisterScreen(
                            modifier = Modifier.padding(innerPadding),
                            patientViewModel,
                            goToLoginScreen = { showLoginScreen = true}
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    patientViewModel: PatientViewModel,
    goToRegisterScreen: () -> Unit) {
    //Variables
    var userId by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var idList by remember { mutableStateOf<List<String>>(emptyList()) }

    // Validation variables
    var passwordPresent by remember { mutableStateOf(false) }
    var idPresent by remember { mutableStateOf(false) }

    // Boolean where true is showing the DropdownMenu and false is closing it.
    var expanded by remember { mutableStateOf(false) }
    val mContext = LocalContext.current

    // Is ran once only.
    LaunchedEffect(Unit) {
        idList = patientViewModel.getAllUserIds()
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

            /**
             * ID Dropdown Section
             */
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
            } else {
                idPresent = true
            }
            Spacer(modifier = Modifier.height(10.dp))

            /**
             * Password Section
             */
            OutlinedTextField(
                value = userPassword,
                onValueChange = {userPassword = it},
                label = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            if (userPassword.length < 1) {
                Text(
                    text = "Please insert your password.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            } else {
                passwordPresent = true
            }
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = "This app is only for pre-registered users. Please enter your ID and password or Register to claim your account on your first visit.",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            /**
             * Continue Button to Log in
             */
            Button(
                onClick = {
                    //isPasswordMatchUser()
//                    if (idPresent && passwordPresent) {
                        // validate pass and stuff here through db
                        // if true {
                            // Success message
//                            Toast.makeText(mContext, "Login Successful", Toast.LENGTH_LONG).show()
//                                // Move to the next page
//                                mContext.startActivity(
//                                    Intent(
//                                        mContext,
//                                        QuestionnairePage::class.java
//                                    )
//                                )
////
//                        } else {
//                            // Error message
//                            Toast.makeText(mContext, "Incorrect Credentials. Please try again.", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                    } else {
////                         Error message
//                        Toast.makeText(mContext, "Please choose your ID and password.", Toast.LENGTH_LONG).show()
//                    }
                }
            ) {
                Text("Continue")
                    }
            Spacer(modifier = Modifier.height(10.dp))


            /**
             * Register Button
             */
            Button(
                onClick = goToRegisterScreen
            ) {
                Text("Register")
            }

        }
    }
}

//@Preview(showBackground = true)
@Composable
//fun RegisterScreen(modifier: Modifier = Modifier) {
fun RegisterScreen(modifier: Modifier = Modifier,
                   patientViewModel: PatientViewModel,
                   goToLoginScreen: () -> Unit) {
    //Variables
    var userId by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userConfirmPassword by remember { mutableStateOf("") }
    var userPhone by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    // For validation
    var phoneNoError by remember { mutableStateOf(false) }
    var matchPassword by remember { mutableStateOf(false) }
    var hasName by remember { mutableStateOf(false) }

    var idList by remember { mutableStateOf<List<String>>(emptyList()) }

    // Boolean where true is showing the DropdownMenu and false is closing it.
    var expanded by remember { mutableStateOf(false) }
    val mContext = LocalContext.current

    // Is ran once only.
    LaunchedEffect(Unit) {
        idList = patientViewModel.getAllUserIds()
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
                text = "Register",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))

            /**
             * ID Section
             */
            OutlinedTextField(
                value = userId,
                onValueChange = {},
                label = { Text(text = "My ID (Provided by your Clinician)") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
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
                idList.forEach { id ->
                    DropdownMenuItem(
                        text = { Text(id) },
                        onClick = {
                            userId = id
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
            Spacer(modifier = Modifier.height(5.dp))

            /**
             * Phone Number Section
             */
            OutlinedTextField(
                value = userPhone,
                onValueChange = {
                    userPhone = it
                    phoneNoError = it.length == 11
                },
                label = { Text(text = "Phone number") },
                modifier = Modifier.fillMaxWidth(),
                // Ensures only numbers can be added
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )
            // Phone number input validation.
            if (!phoneNoError) {
                Text(
                    text = "Phone number must be 11 numbers.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            /**
             * Name Section
             */
            OutlinedTextField(
                value = userName,
                onValueChange = {
                    userName = it
                },
                label = { Text(text = "Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // Name input validation.
            if (userName.length < 1) {
                Text(
                    text = "Please enter your name.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            } else {
                hasName = true
            }
            Spacer(modifier = Modifier.height(5.dp))

            /**
             * First Password Section
             */
            OutlinedTextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                label = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = userConfirmPassword,
                onValueChange = { userConfirmPassword = it },
                label = { Text(text = "Confirm password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            if (userPassword != userConfirmPassword ||
                userPassword.length == 0 || userConfirmPassword.length == 0
            ) {
                Text(
                    text = "Your password does not match, please try again.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                matchPassword = true
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "This app is only for pre-registered users. Please enter your ID, phone number and password to claim your account.",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            /**
             * Register Button
             * save it in the db
             */
            Log.d("000debug", "run it " + patientViewModel.isPhoneMatchUser(userId, userPhone))

            Button(
                onClick = {
                    if (phoneNoError && matchPassword && hasName) {
                        Log.d("debug", "passes first if")
                        Log.d("debug", "passes first if with id and phone: " + userId + " & " + userPhone)
                        Log.d("debug", "run it " + patientViewModel.isPhoneMatchUser(userId, userPhone))
                        if (patientViewModel.isPhoneMatchUser(userId, userPhone)){
                            Log.d("debug", "passes second if")

                            //Insert pass and name in db
                            patientViewModel.claimAccount(userId, userName, userPassword)
                            Toast.makeText(mContext, "Account Claimed", Toast.LENGTH_LONG).show()
                        } else {
                            Log.d("debug", "wrong acc")
                            Toast.makeText(mContext, "Wrong Account, please try again.", Toast.LENGTH_LONG).show()

                        }
                    } else {
                        Toast.makeText(mContext, "Please fill in your credentials.", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text("Register")
            }
            Spacer(modifier = Modifier.height(5.dp))

            /**
             * Login Button
             * move to screen
             */
            Button(
                onClick = goToLoginScreen)
            {
                Text("Login")
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