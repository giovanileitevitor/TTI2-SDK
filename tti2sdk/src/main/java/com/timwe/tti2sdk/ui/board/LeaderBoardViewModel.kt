package com.timwe.tti2sdk.ui.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.init.ButtonKey
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.Board
import com.timwe.tti2sdk.data.entity.Boards
import com.timwe.tti2sdk.data.entity.YourPlace
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.BoardsUseCase
import com.timwe.tti2sdk.domain.EventReportUseCase
import com.timwe.tti2sdk.domain.SharedPrefUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LeaderBoardViewModel(
    private val boardsUseCase: BoardsUseCase,
    private val sharedPrefUseCase: SharedPrefUseCase,
    private val eventReportUseCase: EventReportUseCase
): BasicViewModel() {

    private val _boards = MutableLiveData<Boards>()
    val boards: LiveData<Boards> get() = _boards

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _yourPlace = MutableLiveData<YourPlace>()
    val yourPlace: LiveData<YourPlace> get() = _yourPlace

    private val _boardsAllTime = MutableLiveData<List<Board>>()
    val boardsAllTime: LiveData<List<Board>> get() = _boardsAllTime

    private val _boardsToday = MutableLiveData<List<Board>>()
    val boardsToday: LiveData<List<Board>> get() = _boardsToday

    private val _boardsWeek = MutableLiveData<List<Board>>()
    val boardsWeek: LiveData<List<Board>> get() = _boardsWeek

    private val _boardsMonth = MutableLiveData<List<Board>>()
    val boardsMonth: LiveData<List<Board>> get() = _boardsMonth

    fun getBoards(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)

            try {

                when(val result = boardsUseCase.getBoards()){
                    is SuccessResults -> {
                        _yourPlace.postValue(result.body.yourPlace)
                        _boardsAllTime.postValue(result.body.boardAll)
                        _boardsToday.postValue(result.body.boardToday)
                        _boardsWeek.postValue(result.body.boardWeek)
                        _boardsMonth.postValue(result.body.boardMonth)
                        _loading.postValue(false)
                    }
                    is ErrorResults ->{
                        _error.postValue(ApiError(
                            errorCode = result.error.errorCode,
                            errorMessage = result.error.errorMessage
                        ))
                        _loading.postValue(false)
                    }

                }
            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }

        }
    }

    fun sendEvent(eventType: EventType, eventValue: EventValue){
        viewModelScope.launch(Dispatchers.IO) {
            eventReportUseCase.reportEvent(
                eventType = eventType,
                eventValue = eventValue
            )
        }
    }

}