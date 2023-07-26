package com.timwe.tti2sdk.ui.missions.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.Quiz
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.MissionsUseCase
import com.timwe.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.function.BiPredicate

class DailyViewModel(
    private val missionsUseCase: MissionsUseCase
): BasicViewModel() {

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _loadingProgress = MutableLiveData<Boolean>()
    val loadingProgress: LiveData<Boolean> get() = _loadingProgress

    private val _progressMissionCompleted = MutableLiveData<Boolean>()
    val progressMissionCompleted: LiveData<Boolean> get() = _progressMissionCompleted

    private val _educationMissionCompleted = MutableLiveData<Boolean>()
    val educationMissionCompleted: LiveData<Boolean> get() = _educationMissionCompleted

    private val _loadingEduc = MutableLiveData<Boolean>()
    val loadingEduc: LiveData<Boolean> get() = _loadingEduc

    private val _goToNextQuestion = MutableLiveData<Boolean>()
    val goToNextQuestion: LiveData<Boolean> get() = _goToNextQuestion

    fun getDailyCheckup(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    //Used on Educational Activity
    fun setEducationMissionToCompleted(groupMissionId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingEduc.postValue(true)
                when(val response = missionsUseCase.completeMissions(groupMissionId = groupMissionId)){
                    is SuccessResults -> {
                        val result = response.body
                        _educationMissionCompleted.postValue(true)
                        _loadingEduc.postValue(false)
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = response.error.errorCode,
                            errorMessage = response.error.errorMessage
                        ))
                        _educationMissionCompleted.postValue(false)
                        _loadingEduc.postValue(false)
                    }
                }
            }catch (e: java.lang.Exception){
                //setErrorCallback(e, _error, _loading)
            }
        }
    }

    //Used on Progress Activity
    fun setProgressMissionToCompleted(groupMissionId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingProgress.postValue(true)
                when(val response = missionsUseCase.completeMissions(groupMissionId = groupMissionId)){
                    is SuccessResults -> {
                        val result = response.body
                        _progressMissionCompleted.postValue(true)
                        _loadingProgress.postValue(false)
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = response.error.errorCode,
                            errorMessage = response.error.errorMessage
                        ))
                        _progressMissionCompleted.postValue(false)
                        _loadingProgress.postValue(false)
                    }
                }
            }catch (e: java.lang.Exception){
                //setErrorCallback(e, _error, _loading)
            }
        }
    }

    //Used on Quiz Activity
    fun setQuizValue(){
        viewModelScope.launch(Dispatchers.IO) {


        }
    }

    fun getNextQuestionQuiz(quiz: Quiz, currentPosition: Int){
        viewModelScope.launch(Dispatchers.IO) {
            var totalQuestions = quiz.totalQuestions

            if(currentPosition <= (quiz.questions.size + 1)){
                _goToNextQuestion.postValue(true)
            }
        }
    }

}