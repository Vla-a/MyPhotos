package com.example.myphotos.repositorues

import com.example.myphotos.data.AddPhotoResponce
import com.example.myphotos.data.AddPhotoreqwest
import com.example.myphotos.data.Photo
import com.example.myphotos.database.PhotoDao
import com.example.schoolorgonizer.notes.database.PhotoEntity
import com.example.testcurrency.restApi.RegistrApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PhotoRepository(
    private val registrApi: RegistrApi,
    private val photoDao: PhotoDao
) {

    suspend fun getAddPhoto(body: AddPhotoreqwest, token: String): Photo{
        val addPhoto = registrApi.addPhoto(body, token).body()!!.data
        return Photo(
            id = addPhoto.id,
            url = addPhoto.url,
            date = addPhoto.date,
            lat = addPhoto.lat,
            lng = addPhoto.lng
        )
    }

    suspend fun deleteAddPhoto(id: Long, token: String){
        registrApi.deletePhoto(id,token)
    }

    fun gePhotoList(): Flow<List<Photo>> =
        photoDao.getPhotoList().map { photoEntities ->
            photoEntities.map { photoEntities ->
                Photo(
                    photoEntities.id,
                    photoEntities.url,
                    photoEntities.date,
                    photoEntities.lat,
                    photoEntities.lng
                )
            }
        }

     fun addPhoto(photo: Photo) {
         photo.entity()?.let { photoDao.addPhoto(it) }

    }

    suspend fun deletePhoto(photo: Photo) {

        photo.entity()?.let { photoDao.deletePhoto(it) }
    }

    private fun Photo.entity() = this.url?.let {
        PhotoEntity(this.id,
            it, this.date, this.lat, this.lng)
    }

}



