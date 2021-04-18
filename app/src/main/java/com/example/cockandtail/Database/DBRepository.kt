package com.example.cockandtail.Database

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.example.cockandtail.MyApplication
import javax.inject.Inject

class DBRepository @Inject constructor(application: MyApplication) {

    private lateinit var cocktailDao: CocktailDao

    private lateinit var allcocktail: LiveData<List<cocktailsDb>>

    init {
        val database: RoomDb = RoomDb.getInstance(
            application.applicationContext
        )!!
        cocktailDao = database.cockDao()
        allcocktail = cocktailDao.getAllNotes()
    }

    suspend fun insert(cocktailsDb: cocktailsDb) {
        cocktailDao.insert(cocktailsDb)
    }

    suspend fun deleteAllNotes() {
        cocktailDao.deleteAllNotes()
    }

    fun getAllFavourites(): LiveData<List<cocktailsDb>> {
        return allcocktail
    }
}