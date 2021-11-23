package com.example.myphotos.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myphotos.databinding.FragmentLoginBinding
import com.example.myphotos.databinding.FragmentRegestrationBinding
import com.example.myphotos.viewModeles.UserViewModel
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.viewmodel.ext.android.viewModel

class RegestrationFragment: Fragment() {

val userViewModel: UserViewModel by viewModel()
    private var binding: FragmentRegestrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegestrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding!!.btnSingUp.setOnClickListener {



            }
    }

}