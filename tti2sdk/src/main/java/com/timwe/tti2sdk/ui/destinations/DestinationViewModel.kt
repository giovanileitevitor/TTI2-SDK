package com.timwe.tti2sdk.ui.destinations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.Place
import com.timwe.tti2sdk.data.entity.Prize
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

    private val _prizes = MutableLiveData<List<Prize>>()
    val prizes: LiveData<List<Prize>> get() = _prizes

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> get() = _places


    fun getDetailsFromDestinationId(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(3000)

            val results =  destinationsUsecase.getDestinationDetails(id = id)
            _destinationResult.postValue(
               Destination(
                   id = id,
                   title = results.title,
                   subtitle = results.subtitle,
                   description = results.description,
                   urlLink = results.urlLink,
                   prizes = results.prizes,
                   places = results.places
               )
            )

            _prizes.postValue(results.prizes)
            _places.postValue(results.places)

            _loading.postValue(false)
        }
    }

    fun filterPlaces(type: String){
        viewModelScope.launch {

            //val results = destinationsUsecase.getPlacesFiltered(type = type)


        }
    }

}