package com.example.myphotos.repositorues

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

//    suspend fun getAddPhoto (body: JsonObject): Photo? = registrApi!!.addPhoto(body).body()?.let {
//        registrApi!!.addPhoto(body).body()?.let { it1 ->
//            registrApi!!.addPhoto(body).body()?.let { it2 ->
//                registrApi!!.addPhoto(body).body()?.let { it3 ->
//                    registrApi!!.addPhoto(body).body()?.let { it4 ->
//                        Photo(
//                            id = it.id,
//                            url = it1.url,
//                            date = it2.date,
//                            lat = it3.lat,
//                            lng = it4.lng
//                        )
//                    }
//                }
//            }
//        }
//    }

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

    suspend fun addPhoto(photo: PhotoEntity) {
        photoDao.addPhoto(photo)

    }

    suspend fun deletePhoto(photo: Photo) {

        photoDao.deletePhoto(photo.entity())
    }

    private fun Photo.entity() = PhotoEntity(this.id, this.url,this.date, this.lat, this.lng)

    }



