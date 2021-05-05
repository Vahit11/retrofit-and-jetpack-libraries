package com.vahitkeskin.retrofitandjetpacklibraries.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artsDB")
data class ArtRoom(
    var imageName: String,
    var savedUserName: String,
    var savedHistoryTimeName: String,
    var savedHourTimeName: String,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)