package com.fit2081.fit2081_a3_caileen_34375783

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientViewModel
import kotlin.reflect.typeOf

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val patientViewModel: PatientViewModel by viewModels()
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
                        MyNavHost(navController, patientViewModel)
                    }
                }
            }
        }
    }
}


@Composable
fun HomePage(navController: NavHostController, patientViewModel: PatientViewModel) {
    val mContext = LocalContext.current
    val mID = AuthManager.getPatientId().toString()
    val patientDB by patientViewModel.getPatientById(mID).collectAsStateWithLifecycle(null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        Text(
            text = "Hello,",
            color = Color.DarkGray,
            fontSize = 17.sp,
            textAlign = TextAlign.Start)
        Text(
            text = "$mID", // Prints the ID stored in AuthManager
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "You've already filled in your Food intake Questionnaire, but you can change details here:",
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Button (
                onClick = {
                    mContext.startActivity(Intent(mContext, QuestionnairePage::class.java))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Edit")
            }
        }
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.foodscore_meal),
                contentDescription = "Persona 1",
                modifier = Modifier.size(300.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "My Score",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f) // to put the button on the far right
            )
            Button(onClick = {

                navController.navigate("Insights")
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                ) {
                Text(
                    text = "See all scores",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Arrow next",
                    tint = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
            ){
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Arrow up",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Your Food Quality Score",
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            Text(text = patientDB?.totalScore + "/100",
            color = Color.Green,
            fontWeight = FontWeight.Bold
        )
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "What is the Food Quality Score?",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp)
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.",
            fontSize = 12.sp,
            lineHeight = 17.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "This personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
            fontSize = 12.sp,
            lineHeight = 17.sp
            )
    }
}

@Composable
fun MyNavHost(
    navController: NavHostController,
    patientViewModel: PatientViewModel) {
    Log.d("debug navhost homescreen", "run through navhost")
    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable("Home") {
            Log.d("debug navhost homescreen", "in home comp")
            HomePage(navController, patientViewModel)
        }
        composable("Insights") {
            Log.d("debug navhost homescreen", "in insights comp")
            InsightsScreen(navController, patientViewModel)
        }
        composable("NutriCoach") {
            Log.d("debug navhost homescreen", "in nutri comp")
            NutriCoachPage(navController, patientViewModel)
            // To be implemented next assignment: NutriCoach Screen
        }
        composable("Settings") {
            Log.d("debug navhost homescreen", "in settings comp")
            SettingsPage(navController, patientViewModel)
            // To be implemented next assignment: Settings Screen
        }

    }
}

/**
 * AI Declaration
 *
 * I used ChatGPT to help me understand how I can have the shadow following "Insights"
 * when after I click on "See my scores" as the shadow was still on 'Home', then I tried
 * implementing it on my own with comments.
 */
@Composable
fun BottomBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var selectedItem by remember { mutableIntStateOf(0) }

    val items = listOf(
        "Home",
        "Insights",
        "NutriCoach",
        "Settings"
    )
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    when (item) {
                        "Home" -> Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home"
                        )
                        "Insights" -> Icon(
                            Icons.Outlined.Info,
                            contentDescription = "Insights"
                        )
                        "NutriCoach" -> Icon(
                            Icons.Rounded.Person,
                            contentDescription = "NutriCoach"
                        )
                        "Settings" -> Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                label = {
                    Text(item)
                },
                // This tracks the current route dynamically
                selected = currentRoute == item,
                onClick = {
                    // Update which item is visually selected (shadow)
                    selectedItem = index
                    // Navigates to the selected item
                    navController.navigate(item) {
                        // Prevents backtracking to not stack the screens multiple times
                        popUpTo(navController.graph.startDestinationId) {
                            // Doesn't remove the "home screen" but everything after it
                            inclusive = false
                        }
                        // Ensures the destination is on top
                        // Example: Home -> Home -> Home to just Home
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}