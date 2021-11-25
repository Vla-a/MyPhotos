package com.example.myphotos.repositorues

import com.example.myphotos.data.User
import com.example.testcurrency.restApi.RegistrApi

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

    suspend fun getUser (): User? {

        val getUser = registrApi.getUser().body()!!.data
        return  User(
                        userId = getUser.userId,
                        login = getUser.login,
                        token = getUser.token
                    )
                }
}