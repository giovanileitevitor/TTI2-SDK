package com.timwe.tti2sdk.ui.base.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import androidx.lifecycle.viewModelScope

abstract class BaseViewModel : ViewModel(), LifecycleObserver, KoinComponent {

    val loading: LiveData<Boolean> get() = _loading
    protected val _loading = MutableLiveData<Boolean>()

    val error: LiveData<Boolean> get() = _error
    protected val _error = MutableLiveData<Boolean>()

    protected fun launchDataLoad(
        block: suspend () -> Unit
    ): Job {
        return viewModelScope.launch {
            try {
                _loading.value = true
                block()
            } catch (error: Exception) {
                doOnError(error)
            } finally {
                _loading.value = false
            }
        }
    }

    open fun doOnError(throwable: Throwable) {
        val tt = throwable.message
    }
}