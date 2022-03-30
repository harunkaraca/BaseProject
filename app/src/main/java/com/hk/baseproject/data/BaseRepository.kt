package com.hk.baseproject.data

import com.hk.baseproject.data.model.Country
import com.hk.baseproject.data.source.Result
interface BaseRepository {
    suspend fun getCountryList(forceUpdate:Boolean): Result<List<Country>>
}