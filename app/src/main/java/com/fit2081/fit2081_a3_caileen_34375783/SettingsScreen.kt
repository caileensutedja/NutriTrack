package com.fit2081.fit2081_a3_caileen_34375783

import android.graphics.drawable.Icon
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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

@Preview(showBackground = true)
@Composable
fun SettingsPage(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
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
//                Icon(), 1
//                Icon(), 2
//                Icon(), 3

            }
            Column(modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.dp)){
                Text(
                    text = "Name",
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Phone number",
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "User Id",
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
    }
}