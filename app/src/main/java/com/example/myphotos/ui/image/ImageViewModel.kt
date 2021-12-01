package com.example.myphotos.ui.image

import androidx.lifecycle.*
import com.example.myphotos.data.AddPhotoResponce
import com.example.myphotos.data.AddPhotoreqwest
import com.example.myphotos.data.Photo
import com.example.myphotos.repositorues.NoteRepository
import com.example.myphotos.repositorues.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ImageViewModel(
   val photoRepository: PhotoRepository
): ViewModel() {
    val photoListLiveData: LiveData<List<Photo>> =
       photoRepository.gePhotoList().asLiveData()

    val photoLiveData: MutableLiveData<Photo> = MutableLiveData()

    fun getAddPhoto(body: AddPhotoreqwest, token: String) {
        try {
            viewModelScope.launch {
                val photo =  withContext(Dispatchers.IO) {
                   photoRepository.getAddPhoto(body, token)

                }
                photoLiveData.postValue(photo)
            }
        } catch (e: Exception) {
            print(e)
        }
    }

    fun addPhotoToDatabase(newPhoto: Photo) {
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

    fun deletePhotoNetwork(id: Long, token:String) {
        viewModelScope.launch {
            photoRepository.deleteAddPhoto(id, token)
        }
    }
}