package com.example.myphotos.data

import com.google.gson.annotations.SerializedName

data class AddPhotoResponce (
    @SerializedName("status") val status: Long,
    @SerializedName("data") val data: Data
        )
data class Data (
    @SerializedName("id") val id: Long,
    @SerializedName("url") val url: String?,
    @SerializedName("date") val date: Long,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)