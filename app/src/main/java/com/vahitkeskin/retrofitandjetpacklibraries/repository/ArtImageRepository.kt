package com.vahitkeskin.retrofitandjetpacklibraries.repository

import androidx.lifecycle.LiveData
import com.vahitkeskin.retrofitandjetpacklibraries.api.RetrofitAPI
import com.vahitkeskin.retrofitandjetpacklibraries.model.Model
import com.vahitkeskin.retrofitandjetpacklibraries.roomdb.ArtDao
import com.vahitkeskin.retrofitandjetpacklibraries.roomdb.ArtRoom
import com.vahitkeskin.retrofitandjetpacklibraries.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtImageRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtImageRepositoryInterface {
    override suspend fun insertArtImage(art: ArtRoom) {
        artDao.insertArtRoom(art)
    }

    override suspend fun deleteArtImage(art: ArtRoom) {
        artDao.deleteArtRoom(art)
    }

    override fun getArtImage(): LiveData<List<ArtRoom>> {
        return artDao.observeArtsDB()
    }

    override suspend fun searchImage(imageString: String): Resource<Model> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                return Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }


}