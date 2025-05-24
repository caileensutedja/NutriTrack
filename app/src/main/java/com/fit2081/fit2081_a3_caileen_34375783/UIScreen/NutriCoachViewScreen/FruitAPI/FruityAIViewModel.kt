package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.FruitAPI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.GenAI.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Inspired by Lab Week 7 about Gen AI.
 */
class FruityAIViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun fetchFruitInfo(fruitName: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiService = FruitAPIService.create()
                val response = apiService.getFruitInfo(fruitName)
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
                        _uiState.value = UiState.Error("Empty response body.")
                    }
                } else {
                    _uiState.value = UiState.Error("Unavailable fruit, consider banana, tomato, strawberry, melon, and others!")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Please only type in one fruit")
            }
        }
    }
}
