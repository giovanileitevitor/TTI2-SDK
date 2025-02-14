package com.timwe.tti2sdk.ui.avatar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.init.ButtonKey
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.tti2sdk.data.BasicViewModel
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.DrawerEndMission
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.CreateOrUpdateUserRequest
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.model.request.RequestReedenMission
import com.timwe.tti2sdk.data.model.response.AvatarCustomizationsResponse
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.data.net.data.ConnectivityInterceptor.Companion.ERROR_NO_INTERNET_CONNECTION
import com.timwe.tti2sdk.data.net.data.ConnectivityInterceptor.Companion.ERROR_OTHERS
import com.timwe.tti2sdk.domain.AvatarUseCase
import com.timwe.tti2sdk.domain.EventReportUseCase
import com.timwe.tti2sdk.domain.SharedPrefUseCase
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.BOTTOM_CLOTHES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.BOTTOM_CLOTHES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.GENDER
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_EYE_BROWS
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_EYE_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_HAIR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_HAIR_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_SKIN_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.PROFILE_NAME
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.RIDES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.RIDES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.SHOES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.SHOES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.TOP_CLOTHES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.TOP_CLOTHES_COLOR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.URL

class AvatarViewModel(
    private val avatarUseCase: AvatarUseCase,
    private val sharedPrefUseCase: SharedPrefUseCase,
    private val eventReportUseCase: EventReportUseCase
): BasicViewModel() {

    private val _avatar = MutableLiveData<Avatar>()
    val avatar: LiveData<Avatar> get() = _avatar

    private val _userandavatar = MutableLiveData<UserAndAvatar>()
    val userandavatar: LiveData<UserAndAvatar> get() = _userandavatar

    private val _avatarStructure = MutableLiveData<ByteArray>()
    val avatarStructure: LiveData<ByteArray> get() = _avatarStructure

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _isFirstAccessAvatar = MutableLiveData<Boolean>()
    val isFirstAccessAvatar: LiveData<Boolean> get() = _isFirstAccessAvatar

    private val _drawerEndMission = MutableLiveData<DrawerEndMission>()
    val drawerEndMission: LiveData<DrawerEndMission> get() = _drawerEndMission

    private lateinit var pureCreateOrUpdateUserRequest: CreateOrUpdateUserRequest
    private lateinit var editedOrUpdateUserRequest: CreateOrUpdateUserRequest

    suspend fun saveMission(groupMissionId: Int, userAndAvatar: UserAndAvatar){

        try {

            val requestMission = RequestReedenMission(groupMissionId = groupMissionId)

            when (val resposta = avatarUseCase.saveMissionCompleteAvatar(requestMission = requestMission)) {
                is SuccessResults -> {
                    _userandavatar.postValue(userAndAvatar)

                    val drawerEndMission = DrawerEndMission(
                        titleMission = userAndAvatar.titleMission,
                        subtitleMission = userAndAvatar.subtitleMission,
                        typeDistance = userAndAvatar.typeDistance,
                        distanceMission = userAndAvatar.distanceMission
                    )
                    _drawerEndMission.postValue(drawerEndMission)

                    avatarUseCase.saveFirstAcessavatar(true)

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

    fun getFistAccessAvatar(){
        viewModelScope.launch(Dispatchers.IO) {
            _isFirstAccessAvatar.postValue(avatarUseCase.getFistAccessAvatar())
        }
    }

    fun setEditedAvatar(key: String, value: String){
        when(key){
            PROFILE_NAME -> editedOrUpdateUserRequest.profileName = value
            GENDER ->  editedOrUpdateUserRequest.gender = value
            HEAD_SKIN_COLOR ->  editedOrUpdateUserRequest.skinColor = value
            HEAD_HAIR ->  editedOrUpdateUserRequest.hair = value
            HEAD_HAIR_COLOR ->  editedOrUpdateUserRequest.hairColor = value
            HEAD_EYE_COLOR ->  editedOrUpdateUserRequest.eyeColor = value
            HEAD_EYE_BROWS ->  editedOrUpdateUserRequest.eyeBrows = value

            TOP_CLOTHES ->  editedOrUpdateUserRequest.topClothes = value
            TOP_CLOTHES_COLOR ->  editedOrUpdateUserRequest.topClothesColor = value
            BOTTOM_CLOTHES ->  editedOrUpdateUserRequest.bottomClothes = value
            BOTTOM_CLOTHES_COLOR ->  editedOrUpdateUserRequest.bottomClothesColor = value

            SHOES ->  editedOrUpdateUserRequest.shoes = value
            SHOES_COLOR ->  editedOrUpdateUserRequest.shoesColor = value

            RIDES ->  editedOrUpdateUserRequest.rides = value
            RIDES_COLOR ->  editedOrUpdateUserRequest.ridesColor = value
            else ->{
                throw Exception("Avatar parameter not exists")
            }
        }
    }

    private fun setAvatarChosed(profileName: String,
                                gender: String,
                                skinClor: String,
                                hair: String,
                                hairColor: String,
                                eyeColor: String,
                                eyeBrows: String,
                                topClothes: String,
                                topClothesColor: String,
                                bottomClothes: String,
                                bottomClothesColor: String,
                                shoes: String,
                                shoesColor: String,
                                rides: String,
                                ridesColor: String): CreateOrUpdateUserRequest{

        val userAvatarRequest = CreateOrUpdateUserRequest(
            profileName = profileName,
            gender = gender,
            skinColor = skinClor,
            hair = hair,
            hairColor = hairColor,
            eyeColor = eyeColor,
            eyeBrows = eyeBrows,
            topClothes = topClothes,
            topClothesColor = topClothesColor,
            bottomClothes = bottomClothes,
            bottomClothesColor = bottomClothesColor,
            shoes = shoes,
            shoesColor = shoesColor,
            rides = rides,
            ridesColor = ridesColor,
        )

        return userAvatarRequest
    }

    fun getAvatar(random: Boolean = false){
        viewModelScope.launch(Dispatchers.IO){
            _loading.postValue(true)
            delay(2000)

            try {

                when (val resposta = avatarUseCase.getAvatar(random = random)) {
                    is SuccessResults -> {
                        saveAvatarClones(resposta.body)
                        _avatar.postValue(resposta.body)
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

    fun postCreateOrUpdateUser(){
        viewModelScope.launch(Dispatchers.IO){

            try {
                _loading.postValue(true)
                val requestCreateOrUpdateUser = RequestCreateOrUpdateUser(
                    userAvatarRequest = editedOrUpdateUserRequest
                )
                delay(2000)
                when(val resposta = avatarUseCase.postCreatOrUpdateUser(requestCreateOrUpdateUser)){
                    is SuccessResults -> {

                        //Verifico se chamo o outro servico de salvar a missao
                        if( resposta.body.groupMissionId!! > 0){
                            saveMission(resposta.body.groupMissionId!!, resposta.body)

                        }else{
                            _userandavatar.postValue(resposta.body)
                            _loading.postValue(false)
                        }

                    }
                    is ErrorResults -> {
                        _error.postValue(ApiError(
                            errorCode = resposta.error.errorCode,
                            errorMessage = resposta.error.errorMessage
                        ))
                        _loading.postValue(false)
                    }
                }
            }catch (e: Exception){
                setErrorCallback(e, _error, _loading)
            }

        }
    }

    fun getAvatarStructure(){
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val avatarRiveUrl = "https://webportals.cachefly.net/indonesia/telkomsel/tti/v2/riv/avatar.riv"
                _avatarStructure.postValue(
                    URL(avatarRiveUrl).openStream().use {
                        it.readBytes()
                    }
                )
            }catch (e: java.lang.Exception){
                setErrorCallback(e, _error, _loading)
            }

        }
    }

    fun saveAvatarClones(avatar: Avatar) {
        val createOrUpdateUserRequest: CreateOrUpdateUserRequest = setAvatarChosed(
            profileName = avatar.profileName,
            gender = avatar.gender.id.toString(),
            skinClor = avatar.skinColor.id.toString(),
            hair = avatar.hair.id.toString(),
            hairColor = avatar.hairColor.id.toString(),
            eyeColor = avatar.eyeColor.id.toString(),
            eyeBrows = avatar.eyeBrows.id.toString(),
            topClothes = avatar.topClothes.id.toString(),
            topClothesColor = avatar.topClothesColor.id.toString(),
            bottomClothes = avatar.bottomClothes.id.toString(),
            bottomClothesColor = avatar.bottomClothesColor.id.toString(),
            shoes = avatar.shoes.id.toString(),
            shoesColor = avatar.shoesColor.id.toString(),
            rides = avatar.rides.id.toString(),
            ridesColor = avatar.ridesColor.id.toString()
        )
        pureCreateOrUpdateUserRequest = createOrUpdateUserRequest.clone()
        editedOrUpdateUserRequest = createOrUpdateUserRequest.clone()
    }

    fun checkAvatarEdited(): Boolean{
        return pureCreateOrUpdateUserRequest == editedOrUpdateUserRequest
    }

    fun equalsAvatar() {
        pureCreateOrUpdateUserRequest = editedOrUpdateUserRequest
    }

    fun getInitialPosition(avatarCustomizationsResponse: AvatarCustomizationsResponse, key: String): String {
        avatarCustomizationsResponse.customizations.forEach{
            if(it.key == key){
                return it.riveInputKey
            }
        }
        return ""
    }

    fun cancelAll(){
        viewModelScope.cancel()
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