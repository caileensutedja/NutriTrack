package com.fit2081.fit2081_a3_caileen_34375783.FruitAPI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.GenAI.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log


class FruityAIViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    fun fetchFruitInfo(fruitName: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiService = FruitAPIService.create()
                val response = apiService.getFruitInfo(fruitName)
                Log.d("DEBUG FRUITY", "response is: " + response.body())
//                val response2 = apiService.getFruitInfo("banana")
//                Log.d("TAG", "Body: ${response.body()}")
                if (response.isSuccessful) {
                    response.body()?.let { fruit ->
                        val info = fruit.nutritions
                        val output = """
                        Family: ${fruit.family}
                        Calories: ${info.calories}
                        Fat: ${info.fat}
                        Sugar: ${info.sugar}
                        Carbohydrates: ${info.carbohydrates}
                        Protein: ${info.protein}
                    """.trimIndent()

                        _uiState.value = UiState.Success(output)
                    } ?: run {
                        _uiState.value = UiState.Error("Empty response body")
                    }
                } else {
                    _uiState.value = UiState.Error("HTTP ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}

//    fun sendPrompt(
//        prompt: String
//    ) {
//        _uiState.value = UiState.Loading
//
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                // THIS SHOULD GET THE RESPONSE IN THE FORM OF STRING ALR
//                val response = FruitAPIService.getFruitInfo(
//                    content {
//                        text(prompt)
//                    }
//                )
//                response.text?.let { outputContent ->
//                    _uiState.value = UiState.Success(outputContent)
//                }
//            } catch (e: Exception) {
//                _uiState.value = UiState.Error(e.localizedMessage ?: "")
//            }
//        }
//    }
//}

