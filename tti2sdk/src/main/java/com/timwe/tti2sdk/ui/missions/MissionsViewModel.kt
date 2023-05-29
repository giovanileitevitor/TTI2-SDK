package com.timwe.tti2sdk.ui.missions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.AdventureMissions
import com.timwe.tti2sdk.data.entity.BoosterMissions
import com.timwe.tti2sdk.data.entity.DailyMissions
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.entity.Skip
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.domain.MissionsUseCase
import com.timwe.tti2sdk.domain.SharedPrefUseCase
import com.timwe.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MissionsViewModel(
    private val missionsUsecase: MissionsUseCase,
    private val sharedPrefUseCase: SharedPrefUseCase
): BasicViewModel() {

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _missions = MutableLiveData<MissionGroups>()
    val missions: LiveData<MissionGroups> get() = _missions

    private val _dailyMissions = MutableLiveData<DailyMissions>()
    val dailyMissions: LiveData<DailyMissions> get() = _dailyMissions

    private val _adventureMissions = MutableLiveData<AdventureMissions>()
    val adventureMissions: LiveData<AdventureMissions> get() = _adventureMissions

    private val _boosterMissions = MutableLiveData<BoosterMissions>()
    val boosterMissions: LiveData<BoosterMissions> get() = _boosterMissions

    private val _tierType = MutableLiveData<String>()
    val tierType: LiveData<String> get() = _tierType

    private val _skipResult = MutableLiveData<Skip>()
    val skipResult: LiveData<Skip> get() = _skipResult

    init {
        getMissions()
    }


    fun getTierType(){
        viewModelScope.launch {
            _tierType.postValue(sharedPrefUseCase.getAvatarTier() ?: "error")
        }
    }

    fun skipAdventure(groupId: Long? = 0L){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                when(val response = missionsUsecase.skipMissions(groupId = groupId)){
                    is SuccessResults -> {
                        Utils.showLog("SDK", "Skipped groupId successful: $groupId")
                        _skipResult.postValue(response.body)
                        _loading.postValue(false)
                    }
                    is ErrorResults -> {
                        Utils.showLog("SDK", "Skipped groupId failed: $groupId")
                        _error.postValue(ApiError(
                            errorCode = response.error.errorCode,
                            errorMessage = response.error.errorMessage
                        ))
                        _loading.postValue(false)
                    }
                }
            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }
        }
    }

    fun getMissions(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                when(val resposta = missionsUsecase.getMissions()){
                    is SuccessResults -> {
                        _missions.postValue(resposta.body)
                        _dailyMissions.postValue(getDailyMissions(data = resposta.body))
                        _adventureMissions.postValue(getAdventureMissions(data = resposta.body))
                        _boosterMissions.postValue(getBoosterMissions(data = resposta.body))
                        _loading.postValue(false)
                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = resposta.error.errorCode,
                            errorMessage = resposta.error.errorMessage
                        ))
                        _loading.postValue(false)
                    }
                }
            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }

        }
    }


    private fun getDailyMissions(data: MissionGroups): DailyMissions{
        var titleDailyMission = ""
        var dailyMissions = emptyList<Mission2>()

        data.missionGroup.forEach {
            if(it.name == DAILY){
                titleDailyMission = it.description
                dailyMissions = it.missions
            }
        }

        return DailyMissions(
            titleMissions = titleDailyMission,
            dailyMissions = dailyMissions
        )
    }

    private fun getAdventureMissions(data: MissionGroups): AdventureMissions{
        var titleAdventureMission = ""
        var skip = false
        var groupId = 0L
        var adventureMissions = emptyList<Mission2>()

        data.missionGroup.forEach {
            if(it.name == ADVENTURE){
                titleAdventureMission = it.description
                adventureMissions = it.missions
                skip = it.skipAllowed
                groupId = it.groupId
            }
        }

        return AdventureMissions(
            titleAdventure = titleAdventureMission,
            adventureMissions = adventureMissions,
            skipEnabled = skip,
            groupId = groupId
        )
    }

    private fun getBoosterMissions(data: MissionGroups): BoosterMissions{
        var titleBoosterMission = ""
        var boosterMissions = emptyList<Mission2>()

        data.missionGroup.forEach {
            if(it.name == BOOSTER){
                titleBoosterMission = it.description
                boosterMissions = it.missions
            }
        }

        return BoosterMissions(
            titleBooster = titleBoosterMission,
            boosterMissions = boosterMissions
        )
    }


    companion object{
        private const val DAILY = "Daily"
        private const val ADVENTURE = "Adventure"
        private const val BOOSTER = "Booster"
    }
}
