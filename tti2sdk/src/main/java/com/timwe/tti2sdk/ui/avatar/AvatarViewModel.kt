package com.timwe.tti2sdk.ui.avatar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.data.net.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URL

class AvatarViewModel(
    private val repoRepository: RepoRepository
): ViewModel() {

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

    fun getAvatar(){
        viewModelScope.launch(Dispatchers.IO){
            _loading.postValue(true)
            delay(2000)
            when (val resposta = repoRepository.getAvatar(random = true)) {
                is SuccessResults -> {
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
        }
    }

    fun postCreateOrUpdateUser(userAvatar: RequestCreateOrUpdateUser){
        viewModelScope.launch(Dispatchers.IO){
            _loading.postValue(true)
            delay(2000)
            when(val resposta = repoRepository.postCreatOrUpdateUser(userAvatar)){
                is SuccessResults -> {
                    _userandavatar.postValue(resposta.body)
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
        }
    }

    fun getAvatarStructure(){
        viewModelScope.launch(Dispatchers.IO) {
            val mapRiveUrl = "https://webportals.cachefly.net/indonesia/telkomsel/tti/v2/riv/map.riv"
            val avatarRiveUrl = "https://webportals.cachefly.net/indonesia/telkomsel/tti/v2/riv/avatar.riv"
            val demoRiveUrl = "https://cdn.rive.app/animations/juice_v7.riv"
            _avatarStructure.postValue(
                URL(avatarRiveUrl).openStream().use {
                    it.readBytes()
                }
            )
        }
    }

}