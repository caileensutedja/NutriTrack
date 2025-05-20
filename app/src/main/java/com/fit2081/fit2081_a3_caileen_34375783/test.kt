package com.fit2081.fit2081_a3_caileen_34375783

import FruityAIViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.fit2081_a3_caileen_34375783.network.GenAIViewModel
import com.fit2081.fit2081_a3_caileen_34375783.network.UiState
import com.fit2081.fit2081_a3_caileen_34375783.ui.theme.FIT2081_A3_Caileen_34375783Theme

class test : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FIT2081_A3_Caileen_34375783Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    GenAIScreen(modifier = Modifier.padding(innerPadding) )
                    FruityUIScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GenAIScreen(modifier: Modifier = Modifier
) {
    val     genAiViewModel: GenAIViewModel = viewModel()

    val placeholderPrompt = stringResource(R.string.prompt_placeholder)
    val placeholderResult = stringResource(R.string.results_placeholder)
    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }
    var result by rememberSaveable { mutableStateOf(placeholderResult) }
    val uiState by genAiViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.app_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )



        Row(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            TextField(
                value = prompt,
                label = { Text(stringResource(R.string.label_prompt)) },
                onValueChange = { prompt = it },
                modifier = Modifier
                    .weight(0.8f)
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically)
            )

            Button(
                onClick = {

                    genAiViewModel.sendPrompt( prompt)
                },
                enabled = prompt.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = stringResource(R.string.action_go))
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


@Preview(showBackground = true)
@Composable
fun FruityUIScreen(
    modifier: Modifier = Modifier
) {
    val fruitAiViewModel: FruityAIViewModel = viewModel()

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