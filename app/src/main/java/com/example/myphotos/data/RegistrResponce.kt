package com.example.myphotos.data

import com.google.gson.annotations.SerializedName



data class RegistrResponce (
    @SerializedName("status") val status: Long,
    @SerializedName("data") val data: Date

        )


data class Date (
    @SerializedName("userId") val userId: Long,
    @SerializedName("login") val login: String,
    @SerializedName("token")val token: String
)