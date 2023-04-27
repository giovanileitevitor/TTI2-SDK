package com.timwe.tti2sdk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.ProfileInfo
import com.timwe.tti2sdk.data.entity.ValidButton
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.AvatarUseCase
import com.timwe.tti2sdk.domain.DestinationsUseCase
import com.timwe.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val avatarUseCase: AvatarUseCase,
    private val destinationsUseCase: DestinationsUseCase
): BasicViewModel() {

    private val _startRiveListener = MutableLiveData<Boolean>()
    val startRiveListener: LiveData<Boolean> get() = _startRiveListener

    private val _itemClicked = MutableLiveData<ValidButton>()
    val itemClicked: LiveData<ValidButton> get() = _itemClicked

    private val _profileInfo = MutableLiveData<ProfileInfo>()
    val profileInfo: LiveData<ProfileInfo> get() = _profileInfo

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    fun getProfileInfo(){
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                when (val resposta = avatarUseCase.getProfileInfos()) {
                    is SuccessResults -> {
                        _profileInfo.postValue(resposta.body)
                        _loading.postValue(false)
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = resposta.error.errorCode,
                            errorMessage = resposta.error.errorMessage
                        ))
                        _loading.postValue(false)
                    }
                }
            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }
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
            delay(100)
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

    fun getCities(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                when (val listCity = destinationsUseCase.getListCities()) {
                    is SuccessResults -> {
                        destinationsUseCase.saveCities(listCity.body)
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = listCity.error.errorCode,
                            errorMessage = listCity.error.errorMessage
                        ))
                    }
                }
                _loading.postValue(false)
            } catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }
        }
    }

    fun getMap(){
//        viewModelScope.launch(Dispatchers.IO) {
//            val mapRiveUrl = "https://webportals.cachefly.net/indonesia/telkomsel/tti/v2/riv/map.riv"
//            _mapStructure.postValue(URL(mapRiveUrl).openStream().use {
//                    it.readBytes()
//                }
//            )
//        }
    }


}