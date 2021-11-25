package com.example.myphotos.repositorues

import android.provider.VoicemailContract
import com.example.myphotos.data.User
import com.example.testcurrency.restApi.RegistrApi
import com.google.gson.JsonObject

class UserRepository(  private val registrApi: RegistrApi) {

    suspend fun getRegist (): User? = registrApi.getRegist().body()?.data?.userId?.let {
        registrApi.getRegist().body()?.data?.login?.let { it1 ->
            registrApi.getRegist().body()?.data?.token?.let { it2 ->
                User(
                    userId = it,
                    login = it1,
                    token = it2
                )
            }
        }
    }

    suspend fun getUser (userDto: JsonObject): User {

        val getUser = registrApi.getUser(userDto)
            .body()!!.data
        return  User(
                        userId = getUser.userId,
                        login = getUser.login,
                        token = getUser.token
                    )
                }
}

