package com.example.cockandtail.Repository

import com.example.cockandtail.UIdata.ApiService
import com.example.cockandtail.UIdata.Model.cocktail
import dagger.Provides
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository  @Inject constructor (private val apiService: ApiService) {

    suspend fun getDrinksByName(cocktailName:String)= apiService.getDrinksByName(cocktailName)

}