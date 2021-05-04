package com.vahitkeskin.retrofitandjetpacklibraries.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtRoom(artRoom: ArtRoom)

    @Delete
    suspend fun deleteArtRoom(artRoom: ArtRoom)

    @Query("SELECT * FROM artsDB")
    fun observeArtsDB(): LiveData<List<ArtRoom>>

}