package com.timwe.tti2sdk.ui.missions.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.domain.MissionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyViewModel(
    private val missionsUseCase: MissionsUseCase
): BasicViewModel() {

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getDailyCheckup(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(false)
        }
    }

}