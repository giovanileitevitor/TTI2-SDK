package com.timwe.tti2sdk.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.data.ConnectivityInterceptor
import java.io.IOException

open class BasicViewModel: ViewModel() {

    fun setErrorCallback(e: java.lang.Exception, _error: MutableLiveData<ApiError>, _loading: MutableLiveData<Boolean>) {
        var errorCode = ""
        var errorMessage = ""
        if (e is IOException) {
            errorCode = ConnectivityInterceptor.ERROR_NO_INTERNET_CONNECTION
            errorMessage = e.message.toString()
        } else {
            errorCode = ConnectivityInterceptor.ERROR_OTHERS
            errorMessage = e.message.toString()
        }
        e.printStackTrace()
        _error.postValue(
            ApiError(
                errorCode = errorCode,
                errorMessage = errorMessage
            )
        )
        _loading.postValue(false)
    }

}