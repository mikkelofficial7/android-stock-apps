package com.example.simplestockapp.usecase

import com.example.simplestockapp.network.response.StockResponse
import com.example.simplestockapp.repository.StockRepository
import kotlinx.coroutines.flow.Flow

class StockUseCase(private val repository: StockRepository) {
    suspend fun getStockData(stockCode: String) : Flow<StockResponse?> {
        return repository.getStockData(stockCode)
    }
}