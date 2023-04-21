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

    fun getMissions(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(2000)

            try {
                when(val resposta = missionsUsecase.getMissions()){
                    is SuccessResults -> {
                        _missions.postValue(resposta.body)
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

    fun getMockedMissions(qtd: Int): ArrayList<Mission>{
        val list = ArrayList<Mission>()
        for(i in 1..qtd) {
            list.add(
                Mission(
                    id = i,
                    type = if(i%2 !=0) "hasActions" else "error",
                    flagColor = "red",
                    flagText = if(i%2 !=0) "Daily" else "Weekly",
                    extraFlagColor = "gold",
                    extraFlagText = "Actions needed",
                    distance = if(i%2 !=0) 3 else 9,
                    distanceUnit = if(i%2 !=0) "km" else "m",
                    title = if(i%2 !=0) "Title 1" else "Title 2",
                    subtitle = if(i%2 !=0) "12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas" else "12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas",
                    extraInfo = if(i%2 !=0) "extra informations 1" else "extra informations 2",
                )
            )
        }
        return list
    }

}