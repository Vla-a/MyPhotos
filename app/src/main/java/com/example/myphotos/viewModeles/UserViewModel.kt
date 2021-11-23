package com.example.myphotos.viewModeles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myphotos.data.User
import com.example.myphotos.repositorues.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException

import org.json.JSONObject




class UserViewModel(
    val userRepository: UserRepository
) : ViewModel() {

    val userLiveData: MutableLiveData<User> = MutableLiveData()
    val userLiveData2: MutableLiveData<User> = MutableLiveData()


    fun getRegist() {

        try {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    userRepository.getRegist()
                }
                userLiveData.postValue(result)
            }

        } catch (e: Exception) {
            print(e)
        }
    }

    init{
        getUser()

    }

    fun getUser() {
        try {
            viewModelScope.launch {
                val res = withContext(Dispatchers.IO) {
                    userRepository.getUser()
                }
                userLiveData2.postValue(res)
            }

        } catch (e: Exception) {
            print(e)
        }
    }

}

