package com.timwe.tti2sdk.ui.destinations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.generateDestination
import com.timwe.tti2sdk.domain.DestinationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DestinationViewModel(
    private val destinationsUsecase: DestinationsUseCase
): ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _destinationResult = MutableLiveData<Destination>()
    val destinationResult: LiveData<Destination> get() = _destinationResult

    fun getDetailsFromDestinationId(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(3000)
            _destinationResult.postValue(generateDestination())
            _loading.postValue(false)
        }
    }

}