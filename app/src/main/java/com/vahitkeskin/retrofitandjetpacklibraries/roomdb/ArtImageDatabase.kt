package com.vahitkeskin.retrofitandjetpacklibraries.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtRoom::class], version = 1)
abstract class ArtImageDatabase : RoomDatabase(){
    abstract fun artDao(): ArtDao
}