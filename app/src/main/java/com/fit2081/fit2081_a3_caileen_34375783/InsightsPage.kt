package com.fit2081.fit2081_a3_caileen_34375783

import android.content.Context
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A1_Caileen_34375783Theme
import java.io.BufferedReader
import java.io.InputStreamReader

class InsightsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FIT2081_A1_Caileen_34375783Theme {
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
fun InsightsScreen(navController: NavHostController) {
    // Variables
    val mContext = LocalContext.current
    val sharedPref = mContext.getSharedPreferences("Assignment1", Context.MODE_PRIVATE)
    val mID = sharedPref.getString("id", "")
    val userData = getDataByID(mContext, "data.csv", mID.toString())

    // Variables for later usage.
    val isFemale = userData[2] == "Female"
    val totalScoreMessage: String = "My total score is " + calculateTotalScore() + "."

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
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
        Spacer(modifier = Modifier.height(25.dp))
        /**
         * Data Insights
         */
        // 1. Vegetables
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Vegetables",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[9].toFloat(), y = 10f)
            } else {
                MySlider(x = userData[8].toFloat(), y = 10f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 2. Fruits
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Fruits",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[20].toFloat(), y = 10f)
            } else {
                MySlider(x = userData[19].toFloat(), y = 10f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 3. Grains and Cereals
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Grains and Cereals",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[30].toFloat(), y = 5f)
            } else {
                MySlider(x = userData[29].toFloat(), y = 5f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 4. Whole Grains
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Whole Grains",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[34].toFloat(), y = 5f)
            } else {
                MySlider(x = userData[33].toFloat(), y = 5f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 5. Meat and Alternatives
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Meat and Alternatives",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[37].toFloat(), y = 10f)
            } else {
                MySlider(x = userData[36].toFloat(), y = 10f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 6. Dairy
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Dairy",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[41].toFloat(), y = 10f)
            } else {
                MySlider(x = userData[40].toFloat(), y = 10f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 7. Water
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Water",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[50].toFloat(), y = 5f)
            } else {
                MySlider(x = userData[49].toFloat(), y = 5f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 8a. Saturated Fats
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Saturated Fats",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[58].toFloat(), y = 5f)
            } else {
                MySlider(x = userData[57].toFloat(), y = 5f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 8b. Unsaturated Fats
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Unsaturated Fats",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[61].toFloat(), y = 5f)
            } else {
                MySlider(x = userData[60].toFloat(), y = 5f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 9. Sodium
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sodium",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[44].toFloat(), y = 10f)
            } else {
                MySlider(x = userData[43].toFloat(), y = 10f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 10. Sugar
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sugar",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[55].toFloat(), y = 10f)
            } else {
                MySlider(x = userData[54].toFloat(), y = 10f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 11. Alcohol
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Alcohol",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[47].toFloat(), y = 5f)
            } else {
                MySlider(x = userData[46].toFloat(), y = 5f)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 12. Discretionary Foods
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Discretionary Foods",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.3f).padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            if (isFemale) {
                MySlider(x = userData[6].toFloat(), y = 10f)
            } else {
                MySlider(x = userData[5].toFloat(), y = 10f)
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Total Score
        Text(
            text = "Total Food Quality Score",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 15.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier.padding(start = 15.dp)
        ){
            if (isFemale) {
                MySlider(x = userData[4].toFloat(), y = 100f)
            } else {
                MySlider(x = userData[3].toFloat(), y = 100f)
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        //Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
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
            Button(
                onClick = {}) {
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

/**
 * Custom slider function as a template.
 */
@Composable
fun MySlider(x: Float, y: Float) {
    // Variable to store the position of the slider
    val sliderPosition = x/y

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 20.dp)
    ) {
        Slider(
            value = sliderPosition, // Sets slider position
            onValueChange = {},
            modifier = Modifier.weight(0.5f).height(10.dp),
            steps = 5, // Step of 5
            enabled = false // Can't edit
        )
        Spacer(modifier = Modifier.width(15.dp))

        // Print value over total on the right side as integers
        Text(text = "${(sliderPosition * y).toInt()}/${y.toInt()}",
            fontSize = 10.sp,
            modifier = Modifier.width(40.dp))
    }
}

/**
 * Function to calculate the total score, it returns the string x/y where x is the score and y is
 * the maximum score.
 */
@Composable
fun calculateTotalScore(): String {
    val mContext = LocalContext.current
    val sharedPref = mContext.getSharedPreferences("Assignment1", Context.MODE_PRIVATE)
    val mID = sharedPref.getString("id", "")
    val userData = getDataByID(mContext, "data.csv", mID.toString())
    val isFemale = userData[2] == "Female"
    // Extracts score from appropriate data field.
    val x = if (isFemale) userData[4].toFloat() else userData[3].toFloat()

    return "${((x / 100) * 100).toInt()}/100"
}

/**
 * This function retrieves the data of a certain user (the row) as a list.
 */
fun getDataByID(context: Context, fileName: String, userID: String): List<String> {
    val assets = context.assets
    var data: List<String> = emptyList()

    try {
        val inputStream = assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))

        // Read each line
        reader.useLines { lines ->
            // Drop the header
            lines.drop(1).forEach { line ->
                // Split data by commas
                val values = line.split(",")
                if (values[1] == userID) {
                    data = values
                    return@forEach
                }
            }
        }
    } catch (e:Exception) {
        e.printStackTrace()
    }
    return data
}