package com.fit2081.fit2081_a3_caileen_34375783

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.fit2081_a3_caileen_34375783.R
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientViewModel
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme

import android.util.Log;
import androidx.compose.runtime.LaunchedEffect
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager

class MainActivity : ComponentActivity() {
    private val patientViewModel: PatientViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FIT2081_A3_Caileen_34375783Theme {
                // Checks if the csv data is loaded
                patientViewModel.initialDB(applicationContext)
                Log.d("debug main act if restart", "auth manager start: "+ AuthManager.getPatientId())
                // Variables
                val context = LocalContext.current
                val patientId = AuthManager.getPatientId()

                LaunchedEffect(patientId) {
                    if (patientId != null) {
                        context.startActivity(Intent(context, QuestionnairePage::class.java))
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (patientId == null) {
                        Log.d("debug main act", "pass wel screen")
                        WelcomeScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "NutriTrack",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.nutritrack_logo),
            contentDescription = "Nutritrack logo",
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "This app provides general health and nutrition information for education purposes only. It is not intended as medical advice, diagnosis, or treatment. Always consult a qualified healthcare professional before making any changes to your diet, exercise, or health regimen.",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth(0.85f)
        )
        Text(
            text = "If you'd like to an Accredited Practicing Dietition (APD), please visit the Monash Nutrition/Dietetics Clinic (discounted rates for students): https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth(0.85f)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                val intent = Intent(context, LoginPage::class.java)
                context.startActivity(intent)},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Login",
                fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Designed by Caileen Sutedja (34375783)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Light
        )
    }

}

