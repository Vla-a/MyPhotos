package com.example.myphotos.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myphotos.databinding.FragmentLoginBinding
import com.example.myphotos.viewModeles.UserViewModel
import com.google.gson.JsonObject
import org.json.JSONException
import org.koin.android.viewmodel.ext.android.viewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.btnSubmit.setOnClickListener {

            if (binding!!.etUserName.text.trim().isNotBlank() && binding!!.etPassword.text.trim()
                    .isNotBlank()
            ) {
                val use = getUser(
                    binding!!.etUserName.text.toString(),
                    binding!!.etPassword.text.toString()
                )
                userViewModel.getUser(use)

                startActivity(
                    Intent(activity, UserActivity::class.java)
                )
            } else {
                Toast.makeText(context, "No text", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun getUser(mail: String, password: String): JsonObject {
        val `object` = JsonObject()
        try {

            `object`.addProperty("login", mail)
            `object`.addProperty("password", password)
        } catch (e: JSONException) {

            e.printStackTrace()
        }
        Log.v("JObj", "$`object`")
        return `object`
    }

}
//"vova.liv@tut.by", "123456789"