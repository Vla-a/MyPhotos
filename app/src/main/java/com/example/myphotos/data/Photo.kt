package com.example.myphotos.data

data class Photo(
    val id: Long,
    val url: String?,
    val date: Long,
    val lat: Double,
    val lng: Double
)