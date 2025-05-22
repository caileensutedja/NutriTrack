package com.fit2081.fit2081_a3_caileen_34375783

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientViewModel
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme


class LoginPage : ComponentActivity() {
    // Patient View Model
    private val patientViewModel: PatientViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showLoginScreen by remember {mutableStateOf(true)}
            FIT2081_A3_Caileen_34375783Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showLoginScreen) {
                      LoginScreen(
                          patientViewModel,
                          goToRegisterScreen = { showLoginScreen = false}
                      )
                    } else {
                        RegisterScreen(
                            patientViewModel,
                            goToLoginScreen = { showLoginScreen = true}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    patientViewModel: PatientViewModel,
    goToRegisterScreen: () -> Unit
) {
    //Variables
    var userId by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    // Obtaining State values from the viewmodel
    val idList by patientViewModel.getAllUserIds().collectAsStateWithLifecycle(emptyList())

    // Validation variables
    var passwordPresent by remember { mutableStateOf(false) }
    var idPresent by remember { mutableStateOf(false) }

    // Boolean where true is showing the DropdownMenu and false is closing it.
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
            if (userPassword.isEmpty()) {
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
            val loginResult by patientViewModel.loginResult.collectAsStateWithLifecycle()

            loginResult?.let { result ->
                when (result) {
                    is PatientViewModel.LoginResult.Success -> {
                        Toast.makeText(context, "Login successful.", Toast.LENGTH_SHORT).show()
                        AuthManager.login(context, result.patient.userID)
                        context.startActivity(Intent(context, QuestionnairePage::class.java))
                    }
                    PatientViewModel.LoginResult.IncorrectPassword -> {
                        Toast.makeText(context, "Incorrect password, please try again.", Toast.LENGTH_SHORT).show()
                    }
                    PatientViewModel.LoginResult.AccountNotClaimed -> {
                        Toast.makeText(context, "Account not claimed. Please register.", Toast.LENGTH_SHORT).show()
                    }
                    // Assuming they can choose an invalid input (real life).
                    PatientViewModel.LoginResult.AccountNotFound -> {
                        Toast.makeText(context, "User ID not found.", Toast.LENGTH_SHORT).show()
                    }
                }

                // Reset result so it doesn't keep firing
                patientViewModel.loginResult.value = null
            }

            Button(
                onClick = {
                    if (userId.isNotEmpty() && userPassword.isNotEmpty()) {
                        patientViewModel.login(userId, userPassword)
                    } else {
                        Toast.makeText(context, "Please fill in your credentials.", Toast.LENGTH_SHORT).show()
                    }
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

@Composable
fun RegisterScreen(
    patientViewModel: PatientViewModel,
    goToLoginScreen: () -> Unit
) {
    Log.d("debug regis", "initial regis func")

    //Variables
    var userId by remember { mutableStateOf("") }
    var userPhone by remember { mutableStateOf("") }

    // Obtaining State values from the viewmodel
    val idList by patientViewModel.getAllUserIds().collectAsStateWithLifecycle(emptyList())

    // For validation
    var phoneNoError by remember { mutableStateOf(false) }

    // Boolean where true is showing the DropdownMenu and false is closing it.
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
            Log.d("debug regis", "initial regis 1")

            Text(
                text = "Register",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Log.d("debug regis", "initial regis 2")

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
            Log.d("debug regis", "initial regis 3")

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
                    // Ensures it only allows numbers
                    if (it.all { char -> char.isDigit() }) {
                        userPhone = it
                        phoneNoError = it.length == 11
                    }
                },
                label = { Text(text = "Phone number") },
                modifier = Modifier.fillMaxWidth(),
                // Ensures only numbers can be added
                keyboardOptions = KeyboardOptions
                    (keyboardType = KeyboardType.Number),
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
            // Creates a coroutine to claim the account and store it in the DB.
            val verifyStatus by patientViewModel.verifyStatus.collectAsStateWithLifecycle()
            Button(
                onClick = { patientViewModel.verifyRegister(userId, userPhone)

                }
            ) {
                Text("Continue Register")
            }
            var showModal by remember { mutableStateOf(false) }

            verifyStatus?.let { _ ->
                when (verifyStatus) {
                    is PatientViewModel.VerifyStatus.InvalidID -> Toast.makeText(context, "The ID is invalid.", Toast.LENGTH_SHORT).show()
                    is PatientViewModel.VerifyStatus.InvalidPhone -> Toast.makeText(context, "Phone number doesn't match the ID.", Toast.LENGTH_SHORT).show()
                    is PatientViewModel.VerifyStatus.AlreadyRegistered -> Toast.makeText(context, "Account already claimed, please log in.", Toast.LENGTH_SHORT).show()
                    is PatientViewModel.VerifyStatus.Success -> showModal = true // <- trigger your modal
                    else -> {
                        Toast.makeText(context, "Verification failed.", Toast.LENGTH_SHORT).show()
                    }
                }

                // Reset result so it doesn't keep firing
                patientViewModel.verifyStatus.value = null
            }

            if (showModal) {
                ClaimAccountDialog(
                    onDismissRequest = { showModal = false },
                    onClaimClick = { name, password, confirmPassword ->
                        patientViewModel.claimRegister(userId, name, password, confirmPassword)
                        showModal = false
                    }
                )
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

@Composable
fun ClaimAccountDialog(
    onDismissRequest: () -> Unit,
    onClaimClick: (String, String, String) -> Unit,
) {
    var userName by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userConfirmPassword by remember { mutableStateOf("") }

    val nameValid = userName.isNotBlank()
    val passwordMatch = userPassword == userConfirmPassword && userPassword.isNotBlank()

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Complete Your Registration",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Name input
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!nameValid) {
                    Text(
                        text = "Please enter your name.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Password
                OutlinedTextField(
                    value = userPassword,
                    onValueChange = { userPassword = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Confirm Password
                OutlinedTextField(
                    value = userConfirmPassword,
                    onValueChange = { userConfirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (!passwordMatch) {
                    Text(
                        text = "Your password does not match, please try again.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            onClaimClick(userName, userPassword, userConfirmPassword)
                        },
                        // Only allowed to click the button then.
                        enabled = nameValid && passwordMatch
                    ) {
                        Text("Register")
                    }
                }
            }
        }
    }
}
