package com.example.simplestockapp.repository

import com.example.simplestockapp.network.Api
import com.example.simplestockapp.network.response.StockResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface StockRepository {
    suspend fun getStockData(stockCode: String): Flow<StockResponse?>
}

class StockRepositoryImpl(private val api: Api) : StockRepository {
    override suspend fun getStockData(stockCode: String): Flow<StockResponse?> {
        return flow {
            emit(api.getStock(stockCode))
        }.flowOn(Dispatchers.IO)
    }
}