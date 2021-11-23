package com.example.myphotos.ui.image

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.example.myphotos.data.Photo
import com.example.myphotos.repositorues.PhotoRepository
import com.example.schoolorgonizer.notes.database.PhotoEntity
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ImageViewModel(
   val photoRepository: PhotoRepository
): ViewModel() {
    val photoListLiveData: LiveData<List<Photo>> =
       photoRepository.gePhotoList().asLiveData()

    val photoLiveData: MutableLiveData<Photo> = MutableLiveData()

//    fun getAddPhoto(body: JsonObject) {
//        try {
//            viewModelScope.launch {
//                val res = withContext(Dispatchers.IO) {
//                    photoRepository.getAddPhoto(body)
//                }
//                photoLiveData.postValue(res)
//            }
//
//        } catch (e: Exception) {
//            print(e)
//        }
//    }

    fun addPhotoToDatabase(image: String, lat: Double, lon: Double) {

        val newPhoto = PhotoEntity(
            1,
            image,
            SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(System.currentTimeMillis()),
            lat,
           lon
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                photoRepository.addPhoto(newPhoto)
            }
        }
    }

    fun deletePhoto(photo: Photo) {
        viewModelScope.launch {
           photoRepository.deletePhoto(photo)
        }
    }


}