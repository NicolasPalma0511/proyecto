package com.miempresa.proyecto

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

fun createApi(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun createCocktailApi(): CocktailAPI {
    return createApi(BASE_URL).create(CocktailAPI::class.java)
}



@Composable
fun PantallaCoctel() {
    val coroutineScope = rememberCoroutineScope()
    val cocktailApi = createCocktailApi()
    var cocktail by remember { mutableStateOf<Coctel?>(null) }

    LaunchedEffect(Unit) {
        try {
            val cocktailResponse = cocktailApi.getRandomCocktail()
            if (cocktailResponse.drinks.isNotEmpty()) {
                cocktail = cocktailResponse.drinks[0]
                Log.d("CocktailApp", "Cóctel obtenido: ${cocktail!!.strDrink}")
            } else {
                Log.e("CocktailApp", "No se pudo obtener ningún cóctel")
            }
        } catch (e: Exception) {
            Log.e("CocktailApp", "Error al obtener un cóctel: ${e.message}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (cocktail != null) {
            Text(
                text = cocktail!!.strDrink,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Instrucciones:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = cocktail!!.strInstructions,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                textAlign = TextAlign.Justify
            )

        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .padding(16.dp)
            )
        }
    }
}





