package com.example.simplestockapp

import android.app.Application
import com.example.simplestockapp.koin.Apimodule
import com.example.simplestockapp.koin.MainAppModule
import com.example.simplestockapp.koin.Repositorymodule
import com.example.simplestockapp.koin.Usecasemodule
import com.example.simplestockapp.koin.Viewmodelmodule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class StockApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@StockApp)
            modules(MainAppModule, Apimodule.apiModule, Repositorymodule.repositoryModule,
                Usecasemodule.useCaseModule, Viewmodelmodule.viewModelModule
            )
        }
    }

}