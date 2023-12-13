package com.miempresa.proyecto

import retrofit2.http.GET

interface CocktailAPI {
    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse
}