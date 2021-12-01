package com.example.myphotos.viewModeles

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myphotos.data.SignUserDtoIn
import com.example.myphotos.data.User
import com.example.myphotos.repositorues.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserViewModel(
    val userRepository: UserRepository
) : ViewModel() {

    val userLiveData: MutableLiveData<User> = MutableLiveData()
    val userLiveData2: MutableLiveData<User> = MutableLiveData()


    fun getRegist(userDto: SignUserDtoIn) {

        try {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    userRepository.getRegist(userDto)
                }
                userLiveData.postValue(result)
            }

        } catch (e: Exception) {
            print(e)
        }
    }



    fun getUser(userDto: SignUserDtoIn) {
        try {
           viewModelScope.launch {
                val res = withContext(Dispatchers.IO) {
                    userRepository.getUser(userDto)
                }
               userLiveData2.postValue(res)
           }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}

