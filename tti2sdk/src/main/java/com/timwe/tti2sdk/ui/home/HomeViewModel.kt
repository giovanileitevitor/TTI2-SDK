package com.timwe.tti2sdk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.entity.ValidButton
import com.timwe.tti2sdk.domain.DestinationsUseCase
import com.timwe.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val destinationsUseCase: DestinationsUseCase
): ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _startRiveListener = MutableLiveData<Boolean>()
    val startRiveListener: LiveData<Boolean> get() = _startRiveListener

    private val _mapStructure = MutableLiveData<ByteArray>()
    val mapStructure: LiveData<ByteArray> get() = _mapStructure

    private val _itemClicked = MutableLiveData<ValidButton>()
    val itemClicked: LiveData<ValidButton> get() = _itemClicked

    fun startLoading(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            _startRiveListener.postValue(false)
            delay(3000)
            _loading.postValue(false)
        }
    }

    fun startRiveListener(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(500)
            _startRiveListener.postValue(true)
            _loading.postValue(false)
        }
    }

    fun processItemClicked(itemClicked: String){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(500)
            val stringFormated = itemClicked
                .removePrefix("SEL_")
                .removePrefix("Sel_")
                .replace(Regex("[a-zA-Z]"), "")
            if(stringFormated.matches(Regex("[0-9]+"))){
                Utils.showLog("SDK", "$stringFormated")
                _itemClicked.postValue(
                    ValidButton(
                        isValidButton = true,
                        buttonName = stringFormated
                    )
                )
            }else{
                Utils.showLog("SDK", "$stringFormated")
                _itemClicked.postValue(
                    ValidButton(
                        isValidButton = false,
                        buttonName = stringFormated
                    )
                )
            }
            _loading.postValue(false)
        }
    }

    fun getMap(){
        viewModelScope.launch(Dispatchers.IO) {
            val mapRiveUrl = "https://webportals.cachefly.net/indonesia/telkomsel/tti/v2/riv/map.riv"
//            _mapStructure.postValue(URL(mapRiveUrl).openStream().use {
//                    it.readBytes()
//                }
//            )
        }
    }


}