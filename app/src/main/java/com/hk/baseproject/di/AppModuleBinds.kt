package com.hk.baseproject.di

import com.hk.baseproject.data.BaseRepository
import com.hk.baseproject.data.DefaultRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultRepository): BaseRepository
}
