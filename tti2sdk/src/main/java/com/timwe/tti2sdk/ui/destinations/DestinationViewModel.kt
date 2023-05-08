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
import com.timwe.tti2sdk.domain.SharedPrefUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DestinationViewModel(
    private val destinationsUseCase: DestinationsUseCase,
    private val sharedPrefUseCase: SharedPrefUseCase
): BasicViewModel() {

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _destinationResult = MutableLiveData<Destination>()
    val destinationResult: LiveData<Destination> get() = _destinationResult

    private val _cityId = MutableLiveData<Long>()
    val cityId: LiveData<Long> get() = _cityId

    fun getCityIdfromCityNumber(cityNumber: Long){
        viewModelScope.launch {
            _cityId.postValue(sharedPrefUseCase.getCityId(cityNumber = cityNumber))
        }
    }

    fun getDetailsFromDestinationId(cityId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                when(val results = destinationsUseCase.getCityInfo(cityId = cityId)){
                    is SuccessResults -> {
                        _destinationResult.postValue(results.body!!)
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
            _loading.postValue(false)
        }
    }

    fun getDestination(): Destination{
        return destinationResult.value!!
    }

}