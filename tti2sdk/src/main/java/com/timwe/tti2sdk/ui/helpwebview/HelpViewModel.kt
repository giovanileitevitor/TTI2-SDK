package com.timwe.tti2sdk.ui.helpwebview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.domain.SharedPrefUseCase
import kotlinx.coroutines.launch

class HelpViewModel(
    private val sharedPrefUseCase: SharedPrefUseCase
): ViewModel() {

    private val _helpInfo = MutableLiveData<UrlAddress>()
    val helpInfo: LiveData<UrlAddress> get() = _helpInfo

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    fun getUrls(){
        viewModelScope.launch {
            _loading.postValue(true)
            _helpInfo.postValue(sharedPrefUseCase.getUrls())
            _loading.postValue(false)
        }
    }

}