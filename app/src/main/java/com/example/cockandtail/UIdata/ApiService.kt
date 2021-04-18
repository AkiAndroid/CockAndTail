package com.example.cockandtail.UIdata

import com.example.cockandtail.UIdata.Model.JsonResponse
import com.example.cockandtail.UIdata.Model.cocktail
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    suspend fun getDrinksByName(
        @Query("s") cocktailName: String
    ): JsonResponse

    suspend fun getDrinksByIngredient():JsonResponse


}