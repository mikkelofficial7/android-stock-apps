package com.example.simplestockapp.koin

import android.content.Context
import com.example.simplestockapp.network.RetrofitBuilder
import com.example.simplestockapp.utility.NetworkHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class NetworkModule  {
    companion object {
        private fun httpInterceptor() = HttpLoggingInterceptor().apply {
            return HttpLoggingInterceptor { message ->
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        fun provideNetworkHandler(context: Context) = NetworkHandler(context)

        fun provideOkHttpClient() : OkHttpClient {
            return RetrofitBuilder.initInterceptor(httpInterceptor())
        }

        fun provideRetrofitService(okHttpClient: OkHttpClient): Retrofit {
            return RetrofitBuilder.initRetrofit(okHttpClient)
        }
    }
}