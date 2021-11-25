package com.example.myphotos.viewModeles

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myphotos.data.User
import com.example.myphotos.repositorues.UserRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


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



    fun getUser(userDto: JsonObject) {
        try {
            viewModelScope.launch {
                val res = withContext(Dispatchers.IO) {
                    userRepository.getUser(userDto)
                }
                userLiveData2.postValue(res)
            }
        } catch (e: Exception) {
            e.printStackTrace()
           Log.e("RTY", "oufhtgrdsttttttttttttttttttttt")
        }
    }


}

