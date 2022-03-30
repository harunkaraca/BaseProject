package com.hk.baseproject.data.source

import com.hk.baseproject.data.model.Country
import kotlinx.coroutines.flow.Flow
import com.hk.baseproject.data.source.Result

interface BaseDataSource {
    suspend fun getCountryList(): Result<List<Country>>
    suspend fun deleteCountry(id:Int)
    suspend fun deleteAllCountries()
    suspend fun saveCountry(country:Country)
}