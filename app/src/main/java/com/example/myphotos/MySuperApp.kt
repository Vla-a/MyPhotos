package com.example.myphotos

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myphotos.database.DatabaseConstructor
import com.example.myphotos.database.PhotoDatabase
import com.example.myphotos.repositorues.NoteRepository
import com.example.myphotos.repositorues.PhotoRepository
import com.example.myphotos.repositorues.UserRepository
import com.example.myphotos.ui.detailImage.NoteViewModel
import com.example.myphotos.ui.image.ImageViewModel
import com.example.myphotos.viewModeles.UserViewModel
import com.example.testcurrency.restApi.RegistrApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MySuperApp : Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MySuperApp)
            modules(listOf(repositoryModule, viewModels, currencyApi, storageModule))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val viewModels = module {
        viewModel { UserViewModel(get()) }
        viewModel { ImageViewModel(get()) }
        viewModel { NoteViewModel(get()) }

    }

    private val repositoryModule = module { //создаем репозитории
        factory { PhotoRepository(get(), get()) }
        factory { UserRepository(get()) }
        factory { NoteRepository(get()) }
    }

    private val currencyApi = module {
        single { RegistrApi.get() }
    }
    private val storageModule = module {
        single { DatabaseConstructor.create(get()) }  //создаем синглтон базы данных
        factory { get<PhotoDatabase>().photoDao() }
        factory { get<PhotoDatabase>().noteDao() }
    }

}