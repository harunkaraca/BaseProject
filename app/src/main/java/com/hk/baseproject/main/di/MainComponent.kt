package com.hk.baseproject.main.di

import com.hk.baseproject.data.service.Api
import com.hk.baseproject.main.MainActivity
import com.hk.baseproject.main.MainFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }
    fun inject(fragment: MainFragment)

}