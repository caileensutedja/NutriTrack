package com.fit2081.fit2081_a3_caileen_34375783

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.fit2081.fit2081_a3_caileen_34375783.FruitAPI.FruityAIViewModel
import com.fit2081.fit2081_a3_caileen_34375783.GenAI.GenAIViewModel
import com.fit2081.fit2081_a3_caileen_34375783.GenAI.UiState
import com.fit2081.fit2081_a3_caileen_34375783.PicSumAPI.PicSumViewModel
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientViewModel
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme


class NutriCoachScreen : ComponentActivity() {
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
fun NutriCoachPage(navController: NavHostController,
                   patientViewModel: PatientViewModel) {
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
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
        //Logic here
        PicSumScreen(modifier = Modifier.fillMaxHeight(0.4f))
//        FruityUIScreen(modifier = Modifier.fillMaxHeight(0.4f))
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(10.dp))
        GenAIScreen(modifier = Modifier.fillMaxHeight(0.4f))
    }
}

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
                    fruitAiViewModel.fetchFruitInfo(fruitName)
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

@Composable
fun GenAIScreen(
    modifier: Modifier
) {
    val genAiViewModel: GenAIViewModel = viewModel()
    val uiState by genAiViewModel.uiState.collectAsState()
    var result by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize()
            .fillMaxHeight(0.5f)
    ) {

        Row(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Button(
                onClick = {
                    genAiViewModel.sendPrompt()
                },
//                enabled = prompt.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "Motivational Message (AI)")
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
            val scrollState = rememberScrollState()
            Text(
                text = result,
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


//@Preview(showBackground = true)
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
