package com.example.myphotos.data

import com.google.gson.annotations.SerializedName

data class AddPhotoResponce (
    @SerializedName("id") val id: Long,
    @SerializedName("url") val url: String,
    @SerializedName("date") val date: Long,
    @SerializedName("lat") val lat: Float,
    @SerializedName("lng") val lng: Float
        )
