package com.timwe.tti2sdk.ui.helpwebview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.init.ButtonKey
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.domain.EventReportUseCase
import com.timwe.tti2sdk.domain.SharedPrefUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HelpViewModel(
    private val sharedPrefUseCase: SharedPrefUseCase,
    private val eventReportUseCase: EventReportUseCase
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

    fun sendEvent(eventType: EventType, eventValue: EventValue){
        viewModelScope.launch(Dispatchers.IO) {
            eventReportUseCase.reportEvent(
                eventType = eventType,
                eventValue = eventValue
            )
        }
    }

}