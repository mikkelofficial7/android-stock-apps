package com.example.simplestockapp.koin

import com.example.simplestockapp.usecase.StockUseCase
import org.koin.dsl.module

class Usecasemodule {
    companion object {
        val useCaseModule = module(override = true) {
            single { StockUseCase(get()) }
        }
    }
}