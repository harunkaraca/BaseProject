package com.hk.baseproject.di

import com.hk.baseproject.login.di.LoginComponent
import com.hk.baseproject.main.di.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        MainComponent::class,
        LoginComponent::class
    ]
)
class SubcomponentsModule