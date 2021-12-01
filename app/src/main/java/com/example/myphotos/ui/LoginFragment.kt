package com.example.myphotos.ui

import android.content.Intent
import android.icu.text.DateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.myphotos.data.SignUserDtoIn
import com.example.myphotos.databinding.FragmentLoginBinding
import com.example.myphotos.viewModeles.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class LoginFragment : Fragment() {


    val userViewModel: UserViewModel by viewModel()
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var date = Calendar.getInstance()
        Log.e("KEK", date.timeInMillis.toString())

        binding!!.btnSubmit.setOnClickListener {

            if (binding!!.etUserName.text.trim().isNotBlank() && binding!!.etPassword.text.trim()
                    .isNotBlank()
            ) {
                val use = SignUserDtoIn(
//                    binding!!.etUserName.text.toString(),
//                    binding!!.etPassword.text.toString()
                    "vova.liv@tut.by", "123456789"
                )
                userViewModel.getUser(use)

                userViewModel.userLiveData2.observe(this.viewLifecycleOwner,{
                    startActivity(
                        Intent(activity, UserActivity::class.java).apply {
                            putExtra("TOKEN",it.token )
                        }

                    )
                })

            } else {
                Toast.makeText(context, "No text", Toast.LENGTH_SHORT).show()
            }

        }

    }

//    private fun getUser(mail: String, password: String): JsonObject {
//        val `object` = JsonObject()
//        try {
//
//            `object`.addProperty("login", mail)
//            `object`.addProperty("password", password)
//        } catch (e: JSONException) {
//
//            e.printStackTrace()
//        }
//        Log.v("JObj", "$`object`")
//        return `object`
//    }

}
//"vova.liv@tut.by", "123456789"