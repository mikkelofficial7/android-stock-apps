package com.example.simplestockapp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplestockapp.network.response.StockResponse
import com.example.simplestockapp.usecase.StockUseCase
import com.example.simplestockapp.utility.NetworkHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

open class MainActivityVM @Inject constructor(private val stockUseCase: StockUseCase): ViewModel()  {
    private val _isLoadingEvent = MutableLiveData<Boolean>()
    internal fun getLoadingEvent(): MutableLiveData<Boolean> = _isLoadingEvent

    private val _stockEvent = MutableLiveData<StockResponse>()
    internal fun getStockEvent(): MutableLiveData<StockResponse> = _stockEvent

    private val _errorEvent = MutableLiveData<String>()
    internal fun getErrorEvent(): MutableLiveData<String> = _errorEvent

    internal fun getStockData(context: Context, stockCode: String) {
        _isLoadingEvent.postValue(true)
        executeJob(context) {
            safeScopeFun {
                _isLoadingEvent.postValue(false)
                _errorEvent.postValue(it.localizedMessage)
            }.launch(Dispatchers.IO) {
                stockUseCase.getStockData(stockCode)
                    .collectLatest {
                        _isLoadingEvent.postValue(false)
                        _stockEvent.postValue(it)
                    }
            }
        }
    }

    private fun executeJob(context: Context, invoke: () -> Unit) {
        when (NetworkHandler(context).isNetworkAvailable()) {
            true -> invoke()
            else -> {
                _isLoadingEvent.postValue(false)
                _errorEvent.postValue("No internet connection!")
            }
        }
    }

    private fun safeScopeFun(error :(Throwable) -> Unit) : CoroutineScope {
        return viewModelScope + CoroutineExceptionHandler { _, throwable ->
            error.invoke(throwable)
        }
    }
}