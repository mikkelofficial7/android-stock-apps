package com.example.simplestockapp.koin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val MainAppModule = module(override = true) {
    single { NetworkModule.provideOkHttpClient() }
    single { NetworkModule.provideRetrofitService(get()) }
    single { NetworkModule.provideNetworkHandler(androidContext()) }
}