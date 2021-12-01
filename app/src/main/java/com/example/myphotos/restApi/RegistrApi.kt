package com.example.testcurrency.restApi

import androidx.room.Delete
import com.example.myphotos.data.AddPhotoResponce
import com.example.myphotos.data.AddPhotoreqwest
import com.example.myphotos.data.SignUserDtoIn
import com.example.myphotos.data.RegistrResponce
import com.google.gson.JsonObject

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface RegistrApi {


    @POST( "/api/account/signup")
    suspend fun getRegist (
//        @Body userDto : JsonObject = getConvertedinJson()
        @Body userDto: SignUserDtoIn
      ): Response<RegistrResponce>

    @POST( "/api/account/signin")
    suspend fun getUser (
        @Body userDto: SignUserDtoIn
    ): Response<RegistrResponce>


    @POST( "/api/image")
    suspend fun addPhoto(
        @Body imageDtoIn : AddPhotoreqwest,
        @Header("Access-Token") accessToken: String

    ): Response<AddPhotoResponce>

    @DELETE( "/api/image/{id}")
    suspend fun deletePhoto(
        @Path("id") id : Long,
        @Header("Access-Token") accessToken: String

    ): Response<AddPhotoResponce>

// "N1xW7utIXhNo39lsYh1pRibbbTddi6di3ipAGZIVNOyknMpd3wu6aUBmKEe3gLnS"
    companion object {
        private const val BASE_URL = "http://junior.balinasoft.com"

        fun get(): RegistrApi = Retrofit.Builder().baseUrl(BASE_URL)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()
            )
            .build().create(RegistrApi::class.java)
    }
}


