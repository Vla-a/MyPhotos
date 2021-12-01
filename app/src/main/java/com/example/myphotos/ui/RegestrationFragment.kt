package com.example.myphotos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myphotos.data.SignUserDtoIn
import com.example.myphotos.databinding.FragmentRegestrationBinding
import com.example.myphotos.viewModeles.UserViewModel
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

            if (binding!!.etUserName.text.trim().isNotBlank() && binding!!.etPassword.text.trim()
                    .isNotBlank() && binding!!.etTwoPassword.text.isNotBlank()
            ) {

                val use = SignUserDtoIn(
                    binding!!.etUserName.text.toString(),
                   binding!!.etPassword.text.toString()
                )
                userViewModel.getRegist(use)
            }else{
                Toast.makeText(context, "No text", Toast.LENGTH_SHORT).show()
            }

            }
    }

}