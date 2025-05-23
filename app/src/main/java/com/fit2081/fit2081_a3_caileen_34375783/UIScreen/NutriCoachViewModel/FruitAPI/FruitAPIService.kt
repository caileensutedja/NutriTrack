package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewModel.FruitAPI;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface FruitAPIService {
    // Interface for defining the API endpoints.
    // Endpoint to fetch a list of posts.
    @GET("{fruitname}")
    suspend fun getFruitInfo(@Path("fruitname") fruit: String): Response<FruityResponse>


    companion object {

        var BASE_URL = "https://www.fruityvice.com/api/fruit/"

        fun create(): FruitAPIService {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(FruitAPIService::class.java)

        }
    }
}