package com.fit2081.fit2081_a3_caileen_34375783.network

data class FruityResponse(
    var family: String,
    var nutritions: FruityInfo )
data class FruityInfo(
    var calories: Int,
    var fat: Float,
    var sugar: Float,
    var carbohydrates: Int,
    var protein: Int)