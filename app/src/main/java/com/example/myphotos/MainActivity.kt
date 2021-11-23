package com.example.myphotos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myphotos.utilites.LoginRegistrRepository.adapter
import com.example.myphotos.adapters.ViewPagerAdapter
import com.example.myphotos.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarApp)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val tabLayout = binding.tabLayout
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.isUserInputEnabled = false

        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                LOGIN -> {
                    tab.text = getText(R.string.LOG_IN)

                }
                REGISTR -> {
                    tab.text = getText(R.string.SING_UP)
                }
            }
        }.attach()

    }

    companion object {
        private const val LOGIN = 0
        private const val REGISTR = 1
    }
}
