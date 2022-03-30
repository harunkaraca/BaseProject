package com.hk.baseproject.data

import com.hk.baseproject.data.model.Country
import com.hk.baseproject.data.source.BaseDataSource
import com.hk.baseproject.data.source.remote.RemoteDataSource
import com.hk.baseproject.di.AppModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import com.hk.baseproject.data.source.Result.Error
import com.hk.baseproject.data.source.Result.Success
import com.hk.baseproject.data.source.Result
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.IllegalStateException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class DefaultRepository @Inject constructor(
    @AppModule.RemoteDataSource private val remoteDataSource: BaseDataSource,
    @AppModule.LocalDataSource private val localDataSource:BaseDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO):BaseRepository {
    private var cachedCountries: ConcurrentMap<Int,Country>?=null

    override suspend fun getCountryList(forceUpdate:Boolean): Result<List<Country>> {
        return withContext(ioDispatcher){
            if(!forceUpdate) {
                cachedCountries?.let {
                    return@withContext Success(it.values.sortedBy { it.id })
                }
            }
            val countryList=fetchCountriesFromRemoteOrLocal(forceUpdate)
            // Refresh the cache with the new tasks
            (countryList as? Success)?.let { refreshCache(it.data) }

            cachedCountries?.values?.let { countries ->
                return@withContext Success(countries.sortedBy { it.id })
            }

            (countryList as? Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Success(it.data)
                }
            }
            return@withContext Error(Exception("Illegal state"))
        }
    }

    private suspend fun fetchCountriesFromRemoteOrLocal(forceUpdate: Boolean) : Result<List<Country>>{
        //Remote first
        val countryList=remoteDataSource.getCountryList()
        when(countryList){
            is Error-> Timber.w("Remote data source fetch failed "+countryList.exception)
            is Success->{
                refreshCountriesLocalDataSource(countryList.data)
                return countryList
            }
            else->throw IllegalStateException()
        }
        // Don't read from local if it's forced
        if (forceUpdate) {
            return Error(Exception("Can't force refresh: remote data source is unavailable"))
        }
        // Local if remote fails
        val localCountries = localDataSource.getCountryList()
        if (localCountries is Success) return localCountries
        return Error(Exception("Error fetching from remote and local"))
    }

    private suspend fun refreshCountriesLocalDataSource(countries: List<Country>) {
        localDataSource.deleteAllCountries()
        for (country in countries) {
            localDataSource.saveCountry(country)
        }
    }

    private fun refreshCache(tasks: List<Country>) {
        cachedCountries?.clear()
        tasks.sortedBy { it.id }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private inline fun cacheAndPerform(country: Country, perform: (Country) -> Unit) {
        val cachedCountry = cacheCountry(country)
        perform(cachedCountry)
    }

    private fun cacheCountry(country: Country): Country {
        val cachedCountry = Country(country.id,country.countryName,country.capital,country.flagUrl)
        // Create if it doesn't exist.
        if (cachedCountries == null) {
            cachedCountries = ConcurrentHashMap()
        }
        cachedCountries?.put(cachedCountry.id, cachedCountry)
        return cachedCountry
    }

}