package com.timwe.tti2sdk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class HomeViewModel(
    private val localRepository: SharedPrefRepository
): ViewModel() {

    private val _mapStructure = MutableLiveData<ByteArray>()
    val mapStructure: LiveData<ByteArray> get() = _mapStructure

    fun saveData(data: String){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun getdata(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun getMap(){
        viewModelScope.launch(Dispatchers.IO) {
            val mapRiveUrl = "https://webportals.cachefly.net/indonesia/telkomsel/tti/v2/riv/map.riv"
            _mapStructure.postValue(
                URL(mapRiveUrl).openStream().use {
                    it.readBytes()
                }
            )
        }
    }

}