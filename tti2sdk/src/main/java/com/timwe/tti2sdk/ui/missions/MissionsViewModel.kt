package com.timwe.tti2sdk.ui.missions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.domain.MissionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MissionsViewModel(
    private val missionsUsecase: MissionsUseCase
): ViewModel() {

    fun getMissions(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}