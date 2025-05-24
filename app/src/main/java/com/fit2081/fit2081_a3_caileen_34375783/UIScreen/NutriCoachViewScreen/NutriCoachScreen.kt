package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen.BottomBar
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.HomeScreen.MyNavHost
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.FruitAPI.FruityAIViewModel
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.GenAI.GenAIViewModel
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.GenAI.UiState
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.PicSumAPI.PicSumViewModel
import com.fit2081.fit2081_a3_caileen_34375783.data.AuthManager
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme

class NutriCoachScreen : ComponentActivity() {
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
fun NutriCoachPage(navController: NavHostController) {
    val nutriCoachViewModel: NutriCoachViewModel = viewModel()
    val mID = AuthManager.getPatientId().toString()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "NutriCoach",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (nutriCoachViewModel.isFruitScoreOptimal(mID)) {
            PicSumScreen(modifier = Modifier.fillMaxHeight(0.4f))
        } else {
            FruityUIScreen(modifier = Modifier.fillMaxHeight(0.4f))
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))
        GenAIScreen(modifier = Modifier.fillMaxHeight(0.4f))
    }
}

/**
 * The Fruity screen to provide information on fruits when they search it.
 */
@Preview(showBackground = true)
@Composable
fun FruityUIScreen(
    modifier: Modifier = Modifier.fillMaxHeight(0.5f)
) {
    val fruitAiViewModel: FruityAIViewModel = viewModel()

    var fruitName by remember { mutableStateOf("") }
    var resultFruit by remember { mutableStateOf("") }
    val uiFruitState by fruitAiViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = fruitName,
                onValueChange = { fruitName = it },
                modifier = Modifier.fillMaxWidth(0.7f),
                label = { Text("Fruit Name") }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    fruitAiViewModel.fetchFruitInfo(fruitName.lowercase())
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = fruitName.isNotEmpty()
            ) {
                Text("Details",
                    textAlign = TextAlign.Center)
            }
        }

        if (uiFruitState is UiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            var textColor = MaterialTheme.colorScheme.onSurface
            if (uiFruitState is UiState.Error) {
                textColor = MaterialTheme.colorScheme.error
                resultFruit = (uiFruitState as UiState.Error).errorMessage
            } else if (uiFruitState is UiState.Success) {
                textColor = MaterialTheme.colorScheme.onSurface
                resultFruit = (uiFruitState as UiState.Success).outputText
            }
            val scrollState = rememberScrollState()
            Text(
                text = resultFruit,
                textAlign = TextAlign.Start,
                color = textColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            )
        }
    }
}

/**
 * The GenAI screen to provide tips generated by the model.
 */
@Composable
fun GenAIScreen(
    modifier: Modifier
) {
    val genAiViewModel: GenAIViewModel = viewModel()
    val uiState by genAiViewModel.uiState.collectAsState()
    var result by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(0.5f)
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp).fillMaxWidth()
        ) {
            Button(
                onClick = {
                    genAiViewModel.sendPrompt()
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "Motivational Message (AI)")
            }
            Spacer(modifier = Modifier.weight(1f))
            var showTips = remember { mutableStateOf(false) }
            var allTips = genAiViewModel.fetchAllTips()
            Button(
                onClick = {
                    showTips.value = !showTips.value
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Show all tips" )
            }
            if (showTips.value) {
                tipsDialog(
                    tips = allTips,
                    onDismiss = { showTips.value = false }
                )
            }
        }

        if (uiState is UiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            var textColor = MaterialTheme.colorScheme.onSurface
            if (uiState is UiState.Error) {
                textColor = MaterialTheme.colorScheme.error
                result = (uiState as UiState.Error).errorMessage
            } else if (uiState is UiState.Success) {
                textColor = MaterialTheme.colorScheme.onSurface
                result = (uiState as UiState.Success).outputText
            }
            Text(
                text = result,
                textAlign = TextAlign.Start,
                color = textColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }
    }
}

/**
 * Shows all tips stored from the Database in LazyColumn format.
 *
 * Inspired by Week 4's Lecture about Lazy Column.
 */
@Composable
fun tipsDialog(tips: List<String>, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = "AI Tips",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .padding(horizontal = 16.dp)
                ) {
                    items(tips) { tip ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = tip,
                                modifier = Modifier.padding(12.dp),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text(text = "Done", color = Color.White)
                }
            }
        }
    }
}

/**
 * PicSum screen that shows an image only if fruit score is optimal.
 */
@Composable
fun PicSumScreen(
    modifier: Modifier = Modifier.fillMaxHeight(0.5f)
) {
    val viewModel: PicSumViewModel = viewModel()
    val picsumImage by viewModel.imageUrl.observeAsState()
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        picsumImage?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = "Random Image",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
