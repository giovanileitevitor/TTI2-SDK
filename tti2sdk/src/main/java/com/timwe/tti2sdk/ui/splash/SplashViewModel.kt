package com.timwe.tti2sdk.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.domain.UrlUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(
    private val urlUseCase: UrlUseCase
): ViewModel() {

    private val TIMER = 250

    private val _next = MutableLiveData<Boolean>()
    val next: LiveData<Boolean> get() = _next

    private val _hasUrls = MutableLiveData<List<String>>()
    val hasUrls: LiveData<List<String>> get() = _hasUrls

    fun start(){
        viewModelScope.launch {
            _next.postValue(false)
            android.os.Handler().postDelayed({
                _next.postValue(true)
            }, TIMER.toLong())
        }
    }

    fun getUrls(){
        viewModelScope.launch(Dispatchers.IO) {


        }
    }

}