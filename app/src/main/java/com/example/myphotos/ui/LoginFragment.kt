package com.example.myphotos.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myphotos.databinding.FragmentLoginBinding
import com.example.myphotos.viewModeles.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment: Fragment() {

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

            userViewModel.userLiveData2.observe(this.viewLifecycleOwner,{

                startActivity(
                    Intent(activity, UserActivity::class.java))
            })

        }

    }
}