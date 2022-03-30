package com.hk.baseproject.data.service

import com.hk.baseproject.data.model.Country
import com.hk.baseproject.data.model.BaseApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface Api {

    @GET("/harunkaraca/mockdata/main/mock.json")
    suspend fun getCountries():Response<BaseApiResponse<List<Country>>>
}