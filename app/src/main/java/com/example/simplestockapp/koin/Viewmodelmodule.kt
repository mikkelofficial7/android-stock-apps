package com.example.simplestockapp.koin

import com.example.simplestockapp.MainActivityVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class Viewmodelmodule {
    companion object {
        val viewModelModule = module(override = true) {
            viewModel { MainActivityVM(get()) }
        }
    }
}