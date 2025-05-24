package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.Clinician

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen.BottomBar
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen.MyNavHost
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.GenAI.UiState
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme


class ClinitianScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FIT2081_A3_Caileen_34375783Theme {
                val navController: NavHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBar(navController)
                    }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MyNavHost(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ClinicianLogin(navController: NavHostController) {
    val clinicianViewModel: ClinicianViewModel = viewModel()
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
                text = "Clinician Login",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = clinicianViewModel.clinicianKey.value,
                onValueChange = {clinicianViewModel.clinicianKey.value = it},
                label = { Text(text = "Enter your clinician key") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            if (clinicianViewModel.clinicianKey.value == "") {
                Text(
                    text = "Please insert your password.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))


            Button(
                onClick = {
                    if (clinicianViewModel.attemptLogin()) {
                        navController.navigate("ClinicianDashboard")
                    }

                }
            ) {
                Text("Clinician login")
            }
        }
    }
}

@Composable
fun ClinicianDashboard(navController: NavHostController) {
    val clinicianViewModel: ClinicianViewModel = viewModel()
    val uiState by clinicianViewModel.uiState.collectAsState()
    var result by remember { mutableStateOf("") }
    val context = LocalContext.current


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Clinician Dashboard",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Average HEIFA (Male) : " + clinicianViewModel.maleHEIFAAvg.value,
                fontSize = 15.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Average HEIFA (Female) : "+ clinicianViewModel.femaleHEIFAAvg.value,
                fontSize = 15.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    clinicianViewModel.findDataPattern()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Find Data Pattern")
            }
            Spacer(modifier = Modifier.height(15.dp))
            if (uiState is UiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                if (uiState is UiState.Error) {
                    result = (uiState as UiState.Error).errorMessage
                    Text(result) // Error message
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                } else if (uiState is UiState.Success) {
                    result = (uiState as UiState.Success).outputText
                    PrettyTextView(result)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                        navController.navigate("Settings")
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Done")
            }
        }
    }
}


/**
 * This function aims to create a formatted text of the generated response.
 *
 * AI Declaration
 *
 * I used ChatGPT to help me generate this PrettyTextView function to parse text like bold from
 * the GenAI response.
 */
@Composable
fun PrettyTextView(text: String) {
    // Define scrollable state
    val scrollState = rememberScrollState()

    // Build AnnotatedString to format bold text
    val annotatedText = buildAnnotatedString {
        var startIndex = 0
        while (startIndex < text.length) {
            val boldStart = text.indexOf("**", startIndex)
            if (boldStart != -1) {
                val boldEnd = text.indexOf("**", boldStart + 2)
                if (boldEnd != -1) {
                    // Add normal text before the bold part
                    append(text.substring(startIndex, boldStart))
                    // Add bold text
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(text.substring(boldStart + 2, boldEnd))
                    }
                    // Move to next part after the second "**"
                    startIndex = boldEnd + 2
                } else {
                    break
                }
            } else {
                append(text.substring(startIndex))
                break
            }
        }
    }
    // Display the formatted text inside a scrollable container with a border
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .border(BorderStroke(2.dp, Color.LightGray), shape = RoundedCornerShape(16.dp))
            .padding(16.dp) // Add padding for better spacing
    ) {
        BasicText(text = annotatedText)
    }
}