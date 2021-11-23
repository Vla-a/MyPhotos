package com.example.myphotos.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myphotos.utilites.LoginRegistrRepository.countItem
import com.example.myphotos.ui.LoginFragment
import com.example.myphotos.ui.RegestrationFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return countItem
    }


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            LOGIN -> LoginFragment()
            REGISTR -> RegestrationFragment()
            else -> Fragment()
        }
    }

    companion object {
        private const val LOGIN = 0
        private const val REGISTR = 1
    }
}