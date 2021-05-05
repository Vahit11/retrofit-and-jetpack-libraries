package com.vahitkeskin.retrofitandjetpacklibraries.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahitkeskin.retrofitandjetpacklibraries.model.Model
import com.vahitkeskin.retrofitandjetpacklibraries.repository.ArtImageRepositoryInterface
import com.vahitkeskin.retrofitandjetpacklibraries.roomdb.ArtRoom
import com.vahitkeskin.retrofitandjetpacklibraries.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtImagesViewModel @ViewModelInject constructor(
    private val repository: ArtImageRepositoryInterface
) : ViewModel() {

    val artImageList = repository.getArtImage()

    private val images = MutableLiveData<Resource<Model>>()
    val imagesList: LiveData<Resource<Model>>
        get() = images

    private val selectArtImage = MutableLiveData<String>()
    val selectedArtImageUrl: LiveData<String>
        get() = selectArtImage

    private var insertArtMessage = MutableLiveData<Resource<ArtRoom>>()
    val insertArtMsg: LiveData<Resource<ArtRoom>>
        get() = insertArtMessage

    fun resetArtMessage() {
        insertArtMessage = MutableLiveData<Resource<ArtRoom>>()
    }

    fun setSelectedImage(url: String) {
        selectArtImage.postValue(url)
    }

    fun deleteArtRoom(art: ArtRoom) = viewModelScope.launch {
        repository.deleteArtImage(art)
    }

    fun insertArtRoom(artRoom: ArtRoom) = viewModelScope.launch {
        repository.insertArtImage(artRoom)
    }

    fun makeArt(history: String, hour: String, name: String, user: String) {
        if (history.isEmpty() || hour.isEmpty() || name.isEmpty() || user.isEmpty()) {
            insertArtMessage.postValue(Resource.error("Name, user or time is not null!!!", null))
            return
        }

        val artRoom = ArtRoom(name,user,history,hour,selectedArtImageUrl.value ?: "")
        insertArtRoom(artRoom)
        setSelectedImage("")
        insertArtMessage.postValue(Resource.success(artRoom))
    }

    fun searchForImage(searchImage: String) {
        if (searchImage.isEmpty()) {
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchImage)
            images.value = response
        }
    }
}