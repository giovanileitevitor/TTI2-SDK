package com.timwe.tti2sdk.ui.prizes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.init.ButtonKey
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.model.request.RequestReddemPrize
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.EventReportUseCase
import com.timwe.tti2sdk.domain.PrizeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PrizesViewModel (
    private val prizeUseCase: PrizeUseCase,
    private val eventReportUseCase: EventReportUseCase
): BasicViewModel() {

    private val _prizes = MutableLiveData<PrizeFlow>()
    val prizes: LiveData<PrizeFlow> get() = _prizes

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    fun postRedeemPrize(prizeTokenId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(2000)

            try {
                val requestReddemPrize = RequestReddemPrize(prizeTokenId = prizeTokenId)
                when (val redeem = prizeUseCase.postRedeemPrizes(requestReddemPrize = requestReddemPrize)){
                    is SuccessResults -> {
                        getPrizes()
                    }
                    is ErrorResults -> {
                        _error.postValue(
                            ApiError(
                                errorCode = redeem.error.errorCode,
                                errorMessage = redeem.error.errorMessage
                            )
                        )
                        _loading.postValue(false)
                    }
                }
            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }

        }
    }

    fun getPrizes(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(2000)

            try {

                when (val prizes = prizeUseCase.getPrizes()) {
                    is SuccessResults -> {
                        _prizes.postValue(prizes.body)
                        _loading.postValue(false)
                    }
                    is ErrorResults -> {
                        _error.postValue(
                            ApiError(
                                errorCode = prizes.error.errorCode,
                                errorMessage = prizes.error.errorMessage
                            )
                        )
                        _loading.postValue(false)
                    }
                }

            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }

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