package com.example.simplestockapp.koin

import com.example.simplestockapp.network.Api
import org.koin.dsl.module
import retrofit2.Retrofit

class Apimodule {
    companion object {
        val apiModule = module(override = true) {
            single { provideUserApi(get()) }
        }

        fun provideUserApi(retrofit: Retrofit): Api {
            return retrofit.create(Api::class.java)
        }
    }
}