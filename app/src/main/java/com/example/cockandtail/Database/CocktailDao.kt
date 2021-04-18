package com.example.cockandtail.Database

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CocktailDao {

    @Insert
    fun insert(cocktailsDb: cocktailsDb)

    @Query("DELETE FROM cock_tails")
    fun deleteAllNotes()

    @Query("SELECT * FROM cock_tails")
    fun getAllNotes(): LiveData<List<cocktailsDb>>


}