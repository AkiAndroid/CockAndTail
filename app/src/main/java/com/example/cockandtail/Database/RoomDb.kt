package com.example.cockandtail.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [cocktailsDb::class], version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun cockDao(): CocktailDao

    companion object {

        private var instance: RoomDb? = null

        fun getInstance(context: Context): RoomDb? {
            if (instance == null) {
                synchronized(RoomDb::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDb::class.java, "notes_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}