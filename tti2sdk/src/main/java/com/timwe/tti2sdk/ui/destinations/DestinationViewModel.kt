package com.timwe.tti2sdk.ui.destinations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.DestinationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DestinationViewModel(
    private val destinationsUseCase: DestinationsUseCase
): BasicViewModel() {

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _destinationResult = MutableLiveData<Destination>()
    val destinationResult: LiveData<Destination> get() = _destinationResult

    fun getDetailsFromDestinationId(id: Int = 11950951){

        viewModelScope.launch(Dispatchers.IO) {

            _loading.postValue(true)
            delay(3000)

            try {
                when(val results = destinationsUseCase.getCityInfo(cityId = id)){
                    is SuccessResults -> {
                        _destinationResult.postValue(results.body)
                        _loading.postValue(false)
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = results.error.errorCode,
                            errorMessage = results.error.errorMessage
                        ))
                        _loading.postValue(false)
                    }
                }
            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }
        }
    }

    fun getDetination(): Destination{
        return destinationResult.value!!
    }

}