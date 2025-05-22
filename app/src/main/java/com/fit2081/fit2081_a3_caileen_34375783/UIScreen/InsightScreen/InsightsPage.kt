package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.InsightScreen

import android.content.Intent
import android.content.Intent.ACTION_SEND
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen.BottomBar
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen.MyNavHost
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme

class InsightsPage : ComponentActivity() {
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
fun InsightsScreen(
    navController: NavHostController) {
    val insightViewModel: InsightViewModel = viewModel()
    // Variables
    val mContext = LocalContext.current
    val mID = AuthManager.getPatientId().toString()
    val insightsData = insightViewModel.getInsightsById(mID)
    val totalScore = insightViewModel.getTotalScore(mID)
    val totalScoreMessage = insightViewModel.getTotalScoreMessage(mID)

    Column (
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
//        Log.d("PATIENT DATA RN: ",
//            "veg is: " + patientDB?.vegetableScore
//                    + " and fruit is: " +  patientDB?.fruitScore
//                    + " and grains and cereals is: " + patientDB?.grainsAndCerealScore +
//                    " and whole grains is: " + patientDB?.wholeGrainsScore +
//                    " and meat and alternatives is: " + patientDB?.meatAndAltScore +
//                    " and dairy is: " + patientDB?.dairyAndALtScore +
//                    " and water is: " + patientDB?.waterScore +
//                    " and saturated fats is: " + patientDB?.saturatedFatScore +
//                    " and unsaturated fats is: " + patientDB?.unsaturatedFatScore +
//                    " and sodium is: " + patientDB?.sodiumScore +
//                    " and sugar is: " + patientDB?.sugarScore +
//                    " and alcohol is: " + patientDB?.alcoholScore +
//                    " and discretionary foods is: " + patientDB?.discretionaryScore)

        Spacer(modifier = Modifier.height(25.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text =  "Insights: Food Score",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        /**
         * Data Insights
         */
        FoodScoreInsight(insightsData)
        Spacer(modifier = Modifier.height(20.dp))

        /**
         * Total Food Quality Score Section
         */
        Text(
            text = "Total Food Quality Score",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 15.dp)
        )

        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically)
        {
//            Custom for the total score.
            val sliderPosition = (totalScore.toFloatOrNull() ?: 0f)/ 100f
            Slider(
                value = sliderPosition, // Sets slider position
                onValueChange = {},
                modifier = Modifier.weight(0.5f).padding(start = 15.dp),
                steps = 5, // Step of 5
                enabled = false // Can't edit
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${totalScore} / 100",
                fontSize = 10.sp,
                modifier = Modifier.fillMaxWidth(0.2f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        //Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            /**
             * Button to share total score using Intent.
             */
            Button(onClick = {
                // Intent to share text
                val shareIntent = Intent(ACTION_SEND)
                // Set the type of data to share
                shareIntent.type = "text/plain"
                // Set the data to share (the total score
                shareIntent.putExtra(Intent.EXTRA_TEXT, totalScoreMessage)
                // Starting Activity to share Intent
                mContext.startActivity(Intent.createChooser(shareIntent, "Share text via"))
            }) {
                Text(
                    text = "Share with Someone"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            /**
             * Button to share navigate to the NutriCoach screen..
             */
            Button(
                onClick = {
                    navController.navigate("NutriCoach")
                }) {
                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = "Tool Icon",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Improve my diet!"
                )
            }
        }
    }
}

@Composable
fun FoodScoreInsight(data: List<List<String>>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        data.forEach { insight ->
            val category = insight[0]
            val score = insight[1]
            val total = insight[2]

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .padding(start = 15.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
//                strValue.toIntOrNull() ?: strValue.toFloatOrNull() ?: 0f
                    val sliderPosition = (score.toFloatOrNull() ?: 0f)/ (total.toFloat())
                    Slider(
                        value = sliderPosition, // Sets slider position
                        onValueChange = {},
                        modifier = Modifier.weight(0.5f).height(10.dp),
                        steps = 5, // Step of 5
                        enabled = false // Can't edit
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${score} / ${total}",
                        fontSize = 10.sp,
                        modifier = Modifier.fillMaxWidth(0.2f)
                    )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}