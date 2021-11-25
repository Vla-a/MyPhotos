package com.example.testcurrency.restApi

import android.util.Log
import com.example.myphotos.data.AddPhotoResponce
import com.example.myphotos.data.RegistrResponce
import com.example.myphotos.data.SignUserDtoIn
import com.google.gson.JsonObject

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.Math.log
import java.util.concurrent.TimeUnit
import java.util.*


interface RegistrApi {


    @POST( "/api/account/signup")
    suspend fun getRegist (
//        @Body userDto : JsonObject = getConvertedinJson()
        @Body userDto: JsonObject = getConvertedinJson()
      ): Response<RegistrResponce>

    @POST( "/api/account/signin")
    suspend fun getUser (
        @Body userDto: JsonObject
    ): Response<RegistrResponce>


    @POST( "/api/image")
    suspend fun addPhoto(
        @Body imageDtoIn : JsonObject,
        @Header("Access-Token") accessToken: String = "N1xW7utIXhNo39lsYh1pRibbbTddi6di3ipAGZIVNOyknMpd3wu6aUBmKEe3gLnS"

    ): Response<AddPhotoResponce>


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

private fun getConvertedinJson(): JsonObject {
    val `object` = JsonObject()
    try {

        `object`.addProperty("login", "vladdirec.livvas@tut.by")
        `object`.addProperty("password", "0012345678900ccc")
    } catch (e: JSONException) {

        e.printStackTrace()
    }
    Log.v("JObj", "$`object`")
    return `object`
}

