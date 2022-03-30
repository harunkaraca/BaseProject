package com.hk.baseproject.login.di

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.hk.baseproject.di.ViewModelKey
import com.hk.baseproject.login.LoginRepoInterface
import com.hk.baseproject.login.LoginRepository
import com.hk.baseproject.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
abstract class LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindViewModel(viewmodel: LoginViewModel): ViewModel

    @Singleton
    @Binds
    abstract fun bindLoginRepository(loginRepository: LoginRepository): LoginRepoInterface
}
