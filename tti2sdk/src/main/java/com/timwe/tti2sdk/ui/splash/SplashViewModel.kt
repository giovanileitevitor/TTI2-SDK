package com.timwe.tti2sdk.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.init.UserProfile
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.Decider
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.DestinationsUseCase
import com.timwe.tti2sdk.domain.EventReportUseCase
import com.timwe.tti2sdk.domain.SharedPrefUseCase
import com.timwe.tti2sdk.domain.UrlUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(
    private val urlUseCase: UrlUseCase,
    private val destinationsUseCase: DestinationsUseCase,
    private val sharedPrefUseCase: SharedPrefUseCase,
    private val eventReportUseCase: EventReportUseCase
): BasicViewModel() {

    private val _next = MutableLiveData<Decider>()
    val next: LiveData<Decider> get() = _next

    private val _tokenReceived = MutableLiveData<String>()
    val tokenReceived: LiveData<String> get() = _tokenReceived

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    fun saveDataFromMainApp(avatarProfile: UserProfile, isDebugable: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefUseCase.saveDataFromApp(
                msisdn = avatarProfile.userMsisdn.toLong(),
                email = avatarProfile.email,
                language = avatarProfile.lang,
                tier = avatarProfile.tier,
                plan = avatarProfile.plan
            )
        }
    }

    fun getUrlsAndToken(){
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
                        return@launch
                    }
                }

                when (val resposta = urlUseCase.getUrls()) {
                    is SuccessResults -> {
                        urlUseCase.saveUrls(resposta.body)
                        sharedPrefUseCase.saveCheckupTerms(keyValue = resposta.body.userRegistered)
                        sharedPrefUseCase.saveToken(token = resposta.body.token ?: "")
                        _tokenReceived.postValue(resposta.body.token)
                        _next.postValue(
                            Decider(
                                status = resposta.body.userRegistered,
                                goTo = if(resposta.body.userRegistered) "Home" else "Onboarding",
                            )
                        )
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = resposta.error.errorCode,
                            errorMessage = resposta.error.errorMessage
                        ))
                    }
                }

            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }

        }
    }

    fun sendEvent(eventType: EventType, eventValue: EventValue? = null){
        viewModelScope.launch(Dispatchers.IO) {
            eventReportUseCase.reportEvent(
                eventType = eventType,
                eventValue = null
            )
        }
    }

}