package com.hk.baseproject.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hk.baseproject.data.BaseRepository
import com.hk.baseproject.data.model.Country
import com.hk.baseproject.data.service.Api
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject
import com.hk.baseproject.data.source.Result
import kotlinx.coroutines.delay

//class LoginRepository @Inject constructor(
//    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {
//}
interface LoginRepoInterface {
    suspend fun login(): Result<Boolean>
}
class LoginRepository @Inject constructor(private val api: Api, private val baseRepository: BaseRepository, private val ioDispatcher: CoroutineDispatcher, private val sharedPreferences: SharedPreferences):LoginRepoInterface{

    private val TAG="LoginRepository"
    override suspend fun login(): Result<Boolean> {
        return try {
            delay(1000L)
            Result.Success(true)
        } catch (cause: Exception) {
            Log.e(TAG,cause.message.toString())
            return Result.Error(Exception("Error occured 1"))
        }
    }
}