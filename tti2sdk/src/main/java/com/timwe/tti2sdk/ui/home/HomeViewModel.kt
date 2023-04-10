package com.timwe.tti2sdk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URL

class HomeViewModel(
    private val localRepository: SharedPrefDataSource
): ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _avatarName = MutableLiveData<String>()
    val avatarName: LiveData<String> get() = _avatarName

    private val _mapStructure = MutableLiveData<ByteArray>()
    val mapStructure: LiveData<ByteArray> get() = _mapStructure

    fun saveData(data: String){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun startLoading(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(5000)
            _loading.postValue(false)
        }
    }

    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            _avatarName.postValue("Avatar name AAA ")
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