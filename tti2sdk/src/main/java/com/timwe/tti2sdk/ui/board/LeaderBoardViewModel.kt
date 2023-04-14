package com.timwe.tti2sdk.ui.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.BoardsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LeaderBoardViewModel(
    private val boardsUseCase: BoardsUseCase
): BasicViewModel() {

    private val _boards = MutableLiveData<Boards>()
    val boards: LiveData<Boards> get() = _boards

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    fun getBoards(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(2000)

//            when(val result = boardsUseCase.getBoards()){
//                is SuccessResults -> {
//                    _boards.postValue(result.body)
//                    _loading.postValue(false)
//                }
//                is ErrorResults ->{
//                    ApiError(
//                        errorCode = result.error.errorCode,
//                        errorMessage = result.error.errorMessage
//                    )
//                    _loading.postValue(false)
//                }
//
//            }
            _loading.postValue(false)

        }
    }

}



data class Boards(
    val id: Int,
    val boardAll: List<Board>,
    val boardToday: List<Board>,
    val boardWeek: List<Board>,
    val boardMonth: List<Board>
)

data class Board(
    val id: Int
)