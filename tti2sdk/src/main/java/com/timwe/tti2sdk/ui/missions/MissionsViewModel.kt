package com.timwe.tti2sdk.ui.missions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MissionsViewModel(

): ViewModel() {

    fun getMissions(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}