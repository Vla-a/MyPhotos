package com.example.myphotos.data

data class AddPhotoreqwest(
    val base64Image: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)