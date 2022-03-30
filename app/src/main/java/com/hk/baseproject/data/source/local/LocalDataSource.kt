package com.hk.baseproject.data.source.local

import com.hk.baseproject.data.model.Country
import com.hk.baseproject.data.source.BaseDataSource
import com.hk.baseproject.data.source.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource internal constructor(
    private val countryDao: CountryDao,
    private val ioDispatcher:CoroutineDispatcher = Dispatchers.IO
) : BaseDataSource {

    override suspend fun getCountryList(): Result<List<Country>> = withContext(ioDispatcher) {
        return@withContext try {
            val countries:List<Country> = countryDao.getCountryList().map { Country( it.id,it.name,it.capital,it.flagUrl) }
            Result.Success(countries)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteCountry(id: Int) {
        countryDao.deleteCountryById(id)
    }

    override suspend fun deleteAllCountries() {
        countryDao.deleteAllCountry()
    }

    override suspend fun saveCountry(country: Country) {
        val countryT=CountryT(country.countryName,country.capital,country.flagUrl,country.id!!)
        countryDao.insertCountry(countryT)
    }
}