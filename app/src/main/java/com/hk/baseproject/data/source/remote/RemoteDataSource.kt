package com.hk.baseproject.data.source.remote

import com.hk.baseproject.R
import com.hk.baseproject.data.model.Country
import com.hk.baseproject.data.model.HttpStatusCodes
import com.hk.baseproject.data.service.Api
import com.hk.baseproject.data.source.BaseDataSource
import com.hk.baseproject.data.source.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.hk.baseproject.data.source.Result.Success
import com.hk.baseproject.data.source.Result.Error
import retrofit2.Response

class RemoteDataSource internal constructor(private val api: Api,private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : BaseDataSource {

    override suspend fun getCountryList(): Result<List<Country>> {
        return try {
            api.getCountries().let {response->
                if(response.isSuccessful){
                    when {
                        response.code()==HttpStatusCodes.success ->
                            return Success(response.body()!!.data!!)
                        else ->
                            return Error(Exception("Unhandled http status code returned"))
                    }
                }else return Error(Exception("Error occured"))
            }
        } catch (cause: Exception) {
            return Error(cause)
        }
    }

    override suspend fun deleteCountry(id: Int) {
    }

    override suspend fun deleteAllCountries() {
    }

    override suspend fun saveCountry(country: Country) {
    }

}
