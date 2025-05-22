package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.LoginScreen

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
import androidx.compose.material3.AlertDialog
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
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.QuestionnairePage
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme


class LoginPage : ComponentActivity() {
    // Patient View Model
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showLoginScreen by remember {mutableStateOf(true)}
            FIT2081_A3_Caileen_34375783Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showLoginScreen) {
                      LoginScreen(
                          loginViewModel,
                          goToRegisterScreen = { showLoginScreen = false}
                      )
                    } else {
                        RegisterScreen(
                            loginViewModel,
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
    loginViewModel: LoginViewModel,
    goToRegisterScreen: () -> Unit
) {
    //Variables
    var userId by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    // Obtaining State values from the viewmodel
    val idList by loginViewModel.getAllUserIds().collectAsStateWithLifecycle(emptyList())

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
            val loginResult by loginViewModel.loginResult.collectAsStateWithLifecycle()

            loginResult?.let { result ->
                when (result) {
                    is LoginViewModel.LoginResult.Success -> {
                        Toast.makeText(context, "Login successful.", Toast.LENGTH_SHORT).show()
                        AuthManager.login(context, result.patient.userID)
                        context.startActivity(Intent(context, QuestionnairePage::class.java))
                    }
                    LoginViewModel.LoginResult.IncorrectPassword -> {
                        Toast.makeText(context, "Incorrect password, please try again.", Toast.LENGTH_SHORT).show()
                    }
                    LoginViewModel.LoginResult.AccountNotClaimed -> {
                        Toast.makeText(context, "Account not claimed. Please register.", Toast.LENGTH_SHORT).show()
                    }
                    // Assuming they can choose an invalid input (real life).
                    LoginViewModel.LoginResult.AccountNotFound -> {
                        Toast.makeText(context, "User ID not found.", Toast.LENGTH_SHORT).show()
                    }
                }

                // Reset result so it doesn't keep firing
                loginViewModel.loginResult.value = null
            }

            Button(
                onClick = {
                    if (userId.isNotEmpty() && userPassword.isNotEmpty()) {
                        loginViewModel.login(userId, userPassword)
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
    loginViewModel: LoginViewModel,
    goToLoginScreen: () -> Unit
) {
    Log.d("debug regis", "initial regis func")

    //Variables
    var userId by remember { mutableStateOf("") }
    var userPhone by remember { mutableStateOf("") }

    // Obtaining State values from the viewmodel
    val idList by loginViewModel.getAllUserIds().collectAsStateWithLifecycle(emptyList())

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
            var showModal by remember { mutableStateOf(false) }
            val regisUserDataValidation by loginViewModel.registerUserDataResult.collectAsStateWithLifecycle()

            regisUserDataValidation?.let { result ->
                when (result) {
                    is LoginViewModel.RegisterResult.Success -> {
                        Toast.makeText(context, "Correct credentials.", Toast.LENGTH_SHORT).show()
                        showModal = true
                    }
                    LoginViewModel.RegisterResult.InvalidPhone -> {
                        Toast.makeText(context, "Incorrect phone number, please try again.", Toast.LENGTH_SHORT).show()
                    }
                    LoginViewModel.RegisterResult.AlreadyRegistered -> {
                        Toast.makeText(context, "Account has been claimed, please log in.", Toast.LENGTH_SHORT).show()
                    }
                    // Assuming they can choose an invalid input (real life).
                    LoginViewModel.RegisterResult.AccountNotFound -> {
                        Toast.makeText(context, "User ID not found.", Toast.LENGTH_SHORT).show()
                    }
                }
                // Reset result so it doesn't keep firing
                loginViewModel.registerUserDataResult.value = null
            }
            Button(
                onClick = {
                    loginViewModel.registerUserDataValidation(userId, userPhone)
                },
                enabled = userId.isNotEmpty() && userPhone.length == 11
            ) {
                Text("Continue Register")
            }
            if (showModal) {
                ClaimAccountAlertDialog(
                    userID = userId,
                    loginViewModel = loginViewModel,
                    onDismissRequest = {
                        showModal = false
                        goToLoginScreen() } // Handle dismissal
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
fun ClaimAccountAlertDialog(
    userID: String,
    loginViewModel: LoginViewModel,
    onDismissRequest: () -> Unit // This will handle dismissal
) {
    var context = LocalContext.current
    var userName by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userConfirmPassword by remember { mutableStateOf("") }

    val nameValid = userName.isNotBlank()
    val passwordMatch = userPassword == userConfirmPassword && userPassword.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismissRequest, // Dismiss when clicked outside or on the back button
        confirmButton = {},
        title = {
            Text(
                text = "Complete Your Registration",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                // Password input
                OutlinedTextField(
                    value = userPassword,
                    onValueChange = { userPassword = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Confirm Password input
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
                Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = onDismissRequest) { // Dismiss action
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        loginViewModel.claimRegister(userID, userName, userPassword)
                        if (loginViewModel.claimAccount) {
                            Toast.makeText(context, "Account registered! Please log in.", Toast.LENGTH_SHORT).show()
                            AuthManager.login(context, userID)
                            context.startActivity(Intent(context, QuestionnairePage::class.java))
                        }
                    },
                    enabled = nameValid && passwordMatch
                ) {
                    Text("Register")
                }
            }
            }
        }
    )
}