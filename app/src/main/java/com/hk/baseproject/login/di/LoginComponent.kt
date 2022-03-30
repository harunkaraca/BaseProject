package com.hk.baseproject.login.di

import com.hk.baseproject.login.LoginActivity
import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }
    fun inject(activity: LoginActivity)

}