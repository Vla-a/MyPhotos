package com.example.myphotos.utilites

import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myphotos.adapters.ViewPagerAdapter

@RequiresApi(Build.VERSION_CODES.O)
object LoginRegistrRepository {


    var audioFocusRequest: AudioFocusRequest? = null

    lateinit var adapter: ViewPagerAdapter
    var countItem = 2

    init {
        createAudioFocus()
    }



    private fun createAudioFocus() {
        audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
            setAudioAttributes(AudioAttributes.Builder().run {
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                build()
            })
            build()
        }
    }
}