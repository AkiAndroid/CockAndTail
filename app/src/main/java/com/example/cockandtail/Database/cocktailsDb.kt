package com.example.cockandtail.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "cock_tails")
data class cocktailsDb(

    val id:String,

    val imageurl:String,

    val name: String,

    val instruction:String

) {
    @PrimaryKey(autoGenerate = false)
    var Sid: Int = id.toInt()
}
