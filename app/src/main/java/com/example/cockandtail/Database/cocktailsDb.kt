package com.example.cockandtail.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "cock_tails")
data class cocktailsDb(



    val id:String,


    val instruction:String,


    val name: String,


    val imageurl:String,


    //ingredients

    val ingredient1:String,

    val ingredient2:String,

    val ingredient3:String,

    val ingredient4:String,

    val ingredient5:String,

    val ingredient6:String,

    val ingredient7:String,

    val ingredient8:String,

    val ingredient9:String,

    val ingredient10:String,

    val ingredient11:String,

    val ingredient12:String,

    val ingredient13:String,

    val ingredient14:String,

    val ingredient15:String
)
{
    @PrimaryKey(autoGenerate = true)
    var Sid: Int = 0
}
