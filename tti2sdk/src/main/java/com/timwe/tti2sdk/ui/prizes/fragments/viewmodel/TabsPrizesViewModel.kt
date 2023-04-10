package com.timwe.tti2sdk.ui.prizes.fragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.domain.PrizeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TabsPrizesViewModel(
    private val prizeUseCase: PrizeUseCase
): BasicViewModel() {

    private lateinit var prizeAvailable: PrizeFlow
    private lateinit var historyPrize: PrizeFlow
    private lateinit var purePrize: PrizeFlow

    private val _prizeAvailable = MutableLiveData<PrizeFlow>()
    val prizeAvailableLiveData: LiveData<PrizeFlow> get() = _prizeAvailable

    private val _prizeHistory = MutableLiveData<PrizeFlow>()
    val prizeHistoryLiveData: LiveData<PrizeFlow> get() = _prizeHistory

    fun savePrize(prizeFlow: PrizeFlow) {
        prizeAvailable = prizeFlow.clone()
        historyPrize = prizeFlow.clone()
        purePrize = prizeFlow.clone()

        viewModelScope.launch(Dispatchers.IO){
            _prizeAvailable.postValue(prizeFlow)
        }
    }

    fun setTabHistory(){
        viewModelScope.launch(Dispatchers.IO){
            _prizeHistory.postValue(historyPrize)
        }
    }

}
