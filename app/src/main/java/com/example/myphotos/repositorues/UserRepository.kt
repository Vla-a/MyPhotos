package com.example.myphotos.repositorues

import com.example.myphotos.data.SignUserDtoIn
import com.example.myphotos.data.User
import com.example.testcurrency.restApi.RegistrApi

class UserRepository(private val registrApi: RegistrApi) {

    suspend fun getRegist(userDto: SignUserDtoIn): User {

        val getUser = registrApi.getRegist(userDto).body()!!.data
        return User(
            userId = getUser.userId,
            login = getUser.login,
            token = getUser.token
        )
    }


    suspend fun getUser(userDto: SignUserDtoIn): User {

        val getUser = registrApi.getUser(userDto)
            .body()!!.data
        return User(
            userId = getUser.userId,
            login = getUser.login,
            token = getUser.token
        )
    }
}

