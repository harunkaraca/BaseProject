package com.hk.baseproject.main.di

import androidx.lifecycle.ViewModel
import com.hk.baseproject.data.service.Api
import com.hk.baseproject.di.ViewModelKey
import com.hk.baseproject.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(viewmodel: MainViewModel): ViewModel
}

