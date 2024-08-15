package com.example.simplestockapp.network

import com.example.simplestockapp.network.response.StockResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("chart/{stockCode}")
    suspend fun getStock(@Path("stockCode") stockCode: String): StockResponse
}