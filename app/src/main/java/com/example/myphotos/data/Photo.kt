package com.example.myphotos.data

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class Photo(
    val id: Long,
    val url: String,
    val date: String,
    val lat: Double,
    val lng: Double
)