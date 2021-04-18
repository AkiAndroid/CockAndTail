package com.example.cockandtail.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cockandtail.Database.DBRepository
import com.example.cockandtail.Database.cocktailsDb
import com.example.cockandtail.Repository.MainRepository
import com.example.cockandtail.UIdata.Model.JsonResponse
import com.example.cockandtail.UIdata.Model.cocktail
import com.example.cockandtail.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class MainViewModel (private val mainRepository: MainRepository, private val dbRepository: DBRepository) : ViewModel() {

    var listPopupPosition: Int = -1

    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private var mutableDrinksList : MutableLiveData<Resource<JsonResponse>> = MutableLiveData()
    val drinksList: LiveData<Resource<JsonResponse>> get() = mutableDrinksList

    var favouriteView:MutableLiveData<Boolean> = MutableLiveData(false)

    fun DbOpsofAddfavts(cocktail: cocktail){

        coroutineScope.launch {
            dbRepository.insert(cocktailsDb(cocktail.id,cocktail.imageurl,cocktail.name,cocktail.instruction))
        }
    }

    val getAllFavourites get() = dbRepository.getAllFavourites()


    fun getDrinksByName(cocktailName:String) {
        coroutineScope.launch {
            mutableDrinksList.postValue(Resource.Loading(data = null))
            try {
                mutableDrinksList.postValue(Resource.Success(data = mainRepository.getDrinksByName(cocktailName)))
            } catch (exception: Exception) {
                mutableDrinksList.postValue(Resource.Error(exception.message ?: "Error Occurred!", null))
            }
        }
    }
}