package com.timwe.tti2sdk.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SplashViewModel(

): ViewModel() {

    private val TIMER = 1000

    private val _next = MutableLiveData<Boolean>()
    val next: LiveData<Boolean> get() = _next

    fun start(){
        viewModelScope.launch {
            _next.postValue(false)
            android.os.Handler().postDelayed({
                _next.postValue(true)
            }, TIMER.toLong())
        }
    }

}