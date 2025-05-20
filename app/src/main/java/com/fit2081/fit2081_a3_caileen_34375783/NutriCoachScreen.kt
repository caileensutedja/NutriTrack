package com.fit2081.fit2081_a3_caileen_34375783

import com.fit2081.fit2081_a3_caileen_34375783.FruitAPI.FruityAIViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import com.fit2081.fit2081_a3_caileen_34375783.network.FruitRepository
//import com.fit2081.fit2081_a3_caileen_34375783.network.com.fit2081.fit2081_a3_caileen_34375783.FruitAPI.FruityAIViewModel
import com.fit2081.fit2081_a3_caileen_34375783.GenAI.GenAIViewModel
import com.fit2081.fit2081_a3_caileen_34375783.GenAI.UiState
import com.fit2081.fit2081_a3_caileen_34375783.patient.PatientViewModel
//import com.fit2081.fit2081_a3_caileen_34375783.network.uiState
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

@Preview(showBackground = true)
@Composable
fun NutriCoachPage(
//    fruitAiViewModel: com.fit2081.fit2081_a3_caileen_34375783.FruitAPI.FruityAIViewModel = viewModel(),
    genAiViewModel: GenAIViewModel = viewModel()
) {
    var fruitName by remember { mutableStateOf("") }
//    val placeholderAIPrompt = stringResource(R.string.prompt_placeholder)
    var placeholderAIPrompt by remember { mutableStateOf("") }
    var placeholderAIResult by remember { mutableStateOf("") }
    var promptAI by remember { mutableStateOf("") }
    var resultAI by remember { mutableStateOf("") }

//    val placeholderAIPrompt = stringResource(R.string.prompt_placeholder)
//    val placeholderAIResult = stringResource(R.string.results_placeholder)
//    var prompt by rememberSaveable { mutableStateOf(placeholderAIPrompt) }
//    var result by rememberSaveable { mutableStateOf(placeholderAIResult) }

    val coroutineScope = rememberCoroutineScope()
//    var repository: FruitRepository = FruitRepository()
//    val uiFruitState by fruitAiViewModel.uiState.collectAsState()
    val uiAIState by genAiViewModel.uiState.collectAsState()
//    var AIResponse by rememberSaveable { mutableStateOf(placeholderResult) }

//    var fruit data result

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        OutlinedTextField(
//            value = fruitName,
//            onValueChange = { fruitName = it },
//            label = { Text("Base Currency") }
//        )
//        Button(
//            onClick = {
//                fruitAiViewModel.sendPrompt(fruitName)
//            }
//        ) {
//            Text("Get Rate")
//        }
        OutlinedTextField(
            value = promptAI,
            onValueChange = { promptAI = it },
            label = { Text("AI Input") }
        )
        Button(
            onClick = {
                genAiViewModel.sendPrompt(promptAI)
            },
            enabled = promptAI.isNotEmpty()
        ) {
            Text("gen ai button")
        }
        if (uiAIState is UiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            var textColor = MaterialTheme.colorScheme.onSurface
            if (uiAIState is UiState.Error) {
                textColor = MaterialTheme.colorScheme.error
                resultAI = (uiAIState as UiState.Error).errorMessage
            } else if (uiAIState is UiState.Success) {
                textColor = MaterialTheme.colorScheme.onSurface
                resultAI = (uiAIState as UiState.Success).outputText
            }
            val scrollState = rememberScrollState()
            Text(
                text = resultAI,
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

@Preview(showBackground = true)
@Composable
fun FruityUI(
    fruitAiViewModel: FruityAIViewModel = viewModel(),
) {
    var fruitName by remember { mutableStateOf("") }
//    val placeholderAIPrompt = stringResource(R.string.prompt_placeholder)
    var placeholderAIPrompt by remember { mutableStateOf("") }
    var placeholderAIResult by remember { mutableStateOf("") }
    var resultFruit by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val uiFruitState by fruitAiViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = fruitName,
            onValueChange = { fruitName = it },
            label = { Text("Fruit Name") }
        )
        Button(
            onClick = {
                fruitAiViewModel.fetchFruitInfo(fruitName)
            },
            enabled = fruitName.isNotEmpty()
        ) {
            Text("Find Fruit Info")
        }
//        Text(
//            text = output
//        )
//        OutlinedTextField(
//            value = promptAI,
//            onValueChange = { promptAI = it },
//            label = { Text("AI Input") }
//        )
//        Button(
//            onClick = {
//                genAiViewModel.sendPrompt(promptAI)
//            },
//            enabled = promptAI.isNotEmpty()
//        ) {
//            Text("gen ai button")
//        }
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