package com.timwe.tti2sdk.ui.prizes.fragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.CombinedResultForRecyclerView
import com.timwe.tti2sdk.data.entity.Prize
import com.timwe.tti2sdk.domain.PrizeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TabsPrizesViewModel(
    private val prizeUseCase: PrizeUseCase
): BasicViewModel() {

    private lateinit var prizeAvailable: Prize
    private lateinit var historyPrize: Prize
    private lateinit var purePrize: Prize

    private val _prizeAvailable = MutableLiveData<Prize>()
    val prizeAvailableLiveData: LiveData<Prize> get() = _prizeAvailable

    private val _prizeHistory = MutableLiveData<Prize>()
    val prizeHistoryLiveData: LiveData<Prize> get() = _prizeHistory

    fun savePrize(prize: Prize) {
        prizeAvailable = prize.clone()
        historyPrize = prize.clone()
        purePrize = prize.clone()

        viewModelScope.launch(Dispatchers.IO){
            _prizeAvailable.postValue(prize)
        }

    }

    fun setTabHistory(){
        viewModelScope.launch(Dispatchers.IO){
            _prizeHistory.postValue(historyPrize)
        }
    }

}
