package com.hk.baseproject.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.hk.baseproject.BuildConfig

import com.hk.baseproject.data.service.Api
import com.hk.baseproject.data.source.BaseDataSource
import com.hk.baseproject.data.source.local.MyDatabase
import com.hk.baseproject.data.source.remote.RemoteDataSource
import com.hk.baseproject.data.source.local.LocalDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @JvmStatic
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @JvmStatic
    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return if(BuildConfig.DEBUG){
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }else{
            OkHttpClient.Builder()
                .build()
        }
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @JvmStatic
    @Singleton
    @Provides
    fun api(retrofit: Retrofit) = retrofit.create(Api::class.java)

    @JvmStatic
    @Singleton
    @RemoteDataSource
    @Provides
    fun provideRemoteDataSource(api: Api,ioDispatcher: CoroutineDispatcher) : BaseDataSource {
        return RemoteDataSource(api,ioDispatcher)
    }

    @JvmStatic
    @Singleton
    @LocalDataSource
    @Provides
    fun provideLocalDataSource(database: MyDatabase,ioDispatcher: CoroutineDispatcher): BaseDataSource {
        return LocalDataSource(
            database.countryDao(), ioDispatcher
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): MyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            "Tasks.db"
        ).build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun sharedPreferences(context:Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

}