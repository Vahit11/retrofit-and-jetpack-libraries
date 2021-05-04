package com.vahitkeskin.retrofitandjetpacklibraries.repository

import androidx.lifecycle.LiveData
import com.vahitkeskin.retrofitandjetpacklibraries.model.Model
import com.vahitkeskin.retrofitandjetpacklibraries.roomdb.ArtRoom
import com.vahitkeskin.retrofitandjetpacklibraries.util.Resource
import retrofit2.Response

interface ArtImageRepositoryInterface {

    suspend fun insertArtImage(art: ArtRoom)

    suspend fun deleteArtImage(art: ArtRoom)

    fun getArtImage(): LiveData<List<ArtRoom>>

    suspend fun searchImage(imageString: String): Resource<Model>
}