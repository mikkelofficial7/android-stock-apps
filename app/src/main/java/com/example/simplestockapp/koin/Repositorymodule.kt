package com.example.simplestockapp.koin

import com.example.simplestockapp.repository.StockRepository
import com.example.simplestockapp.repository.StockRepositoryImpl
import org.koin.dsl.module

class Repositorymodule {
    companion object {
        val repositoryModule = module(override = true) {
            single<StockRepository> { return@single StockRepositoryImpl(get()) }
        }
    }
}