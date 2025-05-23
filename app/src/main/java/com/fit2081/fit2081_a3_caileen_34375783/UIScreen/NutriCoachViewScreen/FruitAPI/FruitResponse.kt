package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewScreen.FruitAPI

data class FruityResponse(
    var family: String,
    var nutritions: FruityInfo
)
data class FruityInfo(
    var calories: String,
    var fat: String,
    var sugar: String,
    var carbohydrates: String,
    var protein: String)