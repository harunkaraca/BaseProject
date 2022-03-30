package com.hk.baseproject.di

import android.content.Context
import android.content.SharedPreferences
import com.hk.baseproject.data.BaseRepository
import com.hk.baseproject.data.service.Api
import com.hk.baseproject.login.di.LoginComponent
import com.hk.baseproject.main.di.MainComponent
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,AppModuleBinds::class,ViewModelBuilderModule::class,SubcomponentsModule::class])
interface AppComponent {

//    fun inject(app: MyApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
    fun loginComponent(): LoginComponent.Factory

    val retrofit:Retrofit
    val okHttpClient: OkHttpClient
//    val retrofitBuilder: Retrofit.Builder
    val httpLoggingInterceptor: HttpLoggingInterceptor
//    val api: Api
    val baseRepository: BaseRepository
    val sharedPreferences: SharedPreferences
//    fun injectViewModelFactory():ViewModelProvider.Factory

//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun context(app: MyApplication): Builder
//        fun build(): AppComponent
//    }
}