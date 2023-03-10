package com.timwe.tti2sdk.ui.avatar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.data.net.repository.RepoRepository
import com.timwe.tti2sdk.ui.base.viewmodel.BaseViewModel

class AvatarViewModel(
    private val repoRepository: RepoRepository
): BaseViewModel() {

    private val _avatar = MutableLiveData<Avatar>()
    val avatar: LiveData<Avatar> get() = _avatar

    private val _userandavatar = MutableLiveData<UserAndAvatar>()
    val userandavatar: LiveData<UserAndAvatar> get() = _userandavatar

    fun getAvatar(){
        launchDataLoad{
            when (val resposta = repoRepository.getAvatar(random = true)) {
                is SuccessResults -> {
                    _avatar.postValue(resposta.body)
                }
                is ErrorResults -> {
                    _error.postValue(true)
                }
                else -> {
                    _error.postValue(true)
                }
            }
        }
    }

    fun postCreateOrUpdateUser(userAvatar: RequestCreateOrUpdateUser){
        launchDataLoad {
            when(val resposta = repoRepository.postCreatOrUpdateUser(userAvatar)){
                is SuccessResults ->{
                    _userandavatar.postValue(resposta.body)
                }
                is ErrorResults -> {
                    _error.postValue(true)
                }
            }
        }
    }

    override fun doOnError(throwable: Throwable) {
        super.doOnError(throwable)
        _error.postValue(true)
    }

}