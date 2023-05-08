package com.timwe.tti2sdk.ui.missions.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.MissionsUseCase
import com.timwe.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyViewModel(
    private val missionsUseCase: MissionsUseCase
): BasicViewModel() {

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _educationMissionCompleted = MutableLiveData<Boolean>()
    val educationMissionCompleted: LiveData<Boolean> get() = _educationMissionCompleted

    private val _loadingEduc = MutableLiveData<Boolean>()
    val loadingEduc: LiveData<Boolean> get() = _loadingEduc

    fun getDailyCheckup(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(false)
        }
    }

    //Used on Educational Activity
    fun setMissionToCompleted(groupMissionId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingEduc.postValue(true)
                when(val response = missionsUseCase.completeMissions(groupMissionId = groupMissionId)){
                    is SuccessResults -> {
                        val result = response.body
                        _educationMissionCompleted.postValue(true)
                        _loadingEduc.postValue(false)
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = response.error.errorCode,
                            errorMessage = response.error.errorMessage
                        ))
                        _educationMissionCompleted.postValue(false)
                        _loadingEduc.postValue(false)
                    }
                }
            }catch (e: java.lang.Exception){
                //setErrorCallback(e, _error, _loading)
            }
        }
    }



}