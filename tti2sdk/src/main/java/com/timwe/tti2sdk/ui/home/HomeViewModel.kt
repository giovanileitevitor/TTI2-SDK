package com.timwe.tti2sdk.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val localRepository: SharedPrefRepository
): ViewModel() {

    fun saveData(data: String){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun getdata(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}