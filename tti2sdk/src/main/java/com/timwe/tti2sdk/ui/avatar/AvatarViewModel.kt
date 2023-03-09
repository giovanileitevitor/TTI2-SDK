package com.timwe.tti2sdk.ui.avatar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.tti2sdk.data.net.repository.RepoRepository
import com.timwe.tti2sdk.ui.base.viewmodel.BaseViewModel

class AvatarViewModel(
    private val repoRepository: RepoRepository
): BaseViewModel() {

    private val _avatar = MutableLiveData<Avatar>()

    val avatar: LiveData<Avatar> get() = _avatar

    fun getAvatar(){
        launchDataLoad{
            when (val resposta = repoRepository.getAvatar(random = true)) {
                is SuccessResults -> {
                    _avatar.value = resposta.body
                }
                is ErrorResults -> {
                    _error.value = true
                }
                else -> {
                    _error.value = true
                }
            }
        }
    }

    override fun doOnError(throwable: Throwable) {
        super.doOnError(throwable)
        _error.value = true
    }

}