package com.timwe.tti2sdk.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.UrlUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val urlUseCase: UrlUseCase
): BasicViewModel() {

    private val _next = MutableLiveData<Boolean>()
    val next: LiveData<Boolean> get() = _next

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error


    fun getUrls(){
        viewModelScope.launch(Dispatchers.IO) {

            try {

                _loading.postValue(true)
                delay(2000)

                when (val resposta = urlUseCase.getUrls()) {
                    is SuccessResults -> {
                        urlUseCase.saveUrls(resposta.body)
                        delay(1000)
                        _loading.postValue(false)
                        _next.postValue(true)
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = resposta.error.errorCode,
                            errorMessage = resposta.error.errorMessage
                        ))
                        _loading.postValue(false)
                    }
                }

                _loading.postValue(false)

            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }

        }
    }

}