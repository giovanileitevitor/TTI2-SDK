package com.timwe.tti2sdk.ui.missions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.MissionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MissionsViewModel(
    private val missionsUsecase: MissionsUseCase
): BasicViewModel() {

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _missions = MutableLiveData<MissionGroups>()
    val missions: LiveData<MissionGroups> get() = _missions

    private val _dailyMissions = MutableLiveData<List<Mission>>()
    val dailyMissions: LiveData<List<Mission>> get() = _dailyMissions

    private val _adventureMissions = MutableLiveData<List<Mission>>()
    val adventureMissions: LiveData<List<Mission>> get() = _adventureMissions

    private val _boosterMissions = MutableLiveData<List<Mission>>()
    val boosterMissions: LiveData<List<Mission>> get() = _boosterMissions

    fun getDailyMissions(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                when(val result = missionsUsecase.getDailyMissions()){
                    is SuccessResults -> {
                        _dailyMissions.postValue(result.body)
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = result.error.errorCode,
                            errorMessage = result.error.errorMessage
                        ))
                    }
                }
            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }
        }
    }

    fun getAdventureMissions(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun getBoosterMissions(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun getMissions(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(2000)

            try {
                when(val resposta = missionsUsecase.getMissions()){
                    is SuccessResults -> {
                        _missions.postValue(resposta.body)
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

}