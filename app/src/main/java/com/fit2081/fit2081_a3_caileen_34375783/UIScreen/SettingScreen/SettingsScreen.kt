package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.SettingScreen

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.Clinician.ClinicianLogin
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen.BottomBar
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen.MyNavHost
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.LoginScreen.LoginPage
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme

class SettingsScreen : ComponentActivity() {
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
fun SettingsPage(navHostController: NavHostController){
    val settingViewModel: SettingViewModel = viewModel()

    var context = LocalContext.current
    val mID = AuthManager.getPatientId().toString()
    val name = settingViewModel.getName(mID)
    val phoneNumber = settingViewModel.getPhoneNumber(mID)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
        Text(
            text = "Settings",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(30.dp))
        /**
         * Account section
         */
        Text(
            text = "ACCOUNT",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(0.2f),
                horizontalAlignment = Alignment.Start) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Name",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Icon(
                    imageVector = Icons.Filled.Face,
                    contentDescription = "Phone number",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "ID",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Column(modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.dp)){
                Text(
                    text = name,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = phoneNumber,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = mID,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalDivider()
        /**
         * Other Settings section
         */
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "OTHER SETTINGS",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row (modifier = Modifier.fillMaxWidth()){
            Button(
                onClick = {
                    AuthManager.logout(context)
                    context.startActivity(Intent(context, LoginPage::class.java))
                }
            ){
                Text("Log Out")
            }
        }
        Row (modifier = Modifier.fillMaxWidth()){
            Button(
                onClick = {
                    navHostController.navigate("ClinicianLogin")
                }
            ){
                Text("Clinician Login")
            }
        }
    }
}