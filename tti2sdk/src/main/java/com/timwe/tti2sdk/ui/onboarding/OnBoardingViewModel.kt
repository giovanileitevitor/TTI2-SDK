package com.timwe.tti2sdk.ui.onboarding

import android.content.Context
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.OnboardingInfo
import com.timwe.tti2sdk.domain.SharedPrefUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    //private val context: Context,
    private val sharedPrefUseCase: SharedPrefUseCase
): ViewModel() {

    private val _isProfileCreated = MutableLiveData<Boolean>()
    val isProfileCreated: LiveData<Boolean> get() = _isProfileCreated

    private val _onboardingInfo = MutableLiveData<List<OnboardingInfo>>()
    val onboardingInfo: LiveData<List<OnboardingInfo>> get() = _onboardingInfo

    fun getHelpData(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val onboardingInfoList = ArrayList<OnboardingInfo>()

            //Page 1
            onboardingInfoList.add(
                OnboardingInfo(
                    id = 1,
                    imageDrawable = R.drawable.help_missions_splash_image,
                    txtTitleHelp = context.resources.getString(R.string.txt_help_titleMissions),
                    txtSubtitleHelp = context.resources.getString(R.string.txt_help_descriptionMissions),
                    hasButton = false,
                    buttonText = ""
                )
            )
            //Page 2
            onboardingInfoList.add(
                OnboardingInfo(
                    id = 2,
                    imageDrawable = R.drawable.help_prizes_splash_image,
                    txtTitleHelp = context.resources.getString(R.string.txt_help_titlePrizes),
                    txtSubtitleHelp = context.resources.getString(R.string.txt_help_descriptionPrizes),
                    hasButton = false,
                    buttonText = ""
                )
            )

            //Page 3
            onboardingInfoList.add(
                OnboardingInfo(
                    id = 3,
                    imageDrawable = R.drawable.help_rupiah_splash_image,
                    txtTitleHelp = context.resources.getString(R.string.txt_help_titleRupiahForKM),
                    txtSubtitleHelp = context.resources.getString(R.string.txt_help_descriptionRupiahForKM),
                    hasButton = false,
                    buttonText = ""
                )
            )

            //Page 4
            //onboardingInfoList.add(
            //OnboardingInfo(
            //id = 4,
            //imageDrawable = R.drawable.help_events_splash_image,
            // txtTitleHelp = context.getString(R.string.txt_help_titleEvents),
            //txtSubtitleHelp = context.getString(R.string.txt_help_descriptionEvents),
            //hasButton = false,
            //buttonText = ""
            //)
            //)

            //Page 5
//            onboardingInfoList.add(
//                OnboardingInfo(
//                    id = 5,
//                    imageDrawable = R.drawable.help_tier_rewards_splash_image,
//                    txtTitleHelp = context.resources.getString(R.string.txt_help_titleTiers),
//                    txtSubtitleHelp = context.resources.getString(R.string.txt_help_descriptionTiers),
//                    hasButton = false,
//                    buttonText = ""
//                )
//            )

            //Page 6
            onboardingInfoList.add(
                OnboardingInfo(
                    id = 6,
                    imageDrawable = R.drawable.help_onboarding_7,
                    txtTitleHelp = context.resources.getString(R.string.txt_help_title_ready_travel),
                    txtSubtitleHelp = context.resources.getString(R.string.txt_help_description_ready_travel),
                    hasButton = true,
                    buttonText = context.resources.getString(R.string.txt_help_startAvatar)
                )
            )
            delay(100)
            _onboardingInfo.postValue(onboardingInfoList)
        }
    }

    fun saveCheckedFlag(flagStatus: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            sharedPrefUseCase.saveCheckupTerms(
                keyValue = flagStatus
            )
        }
    }

    fun checkIfProfileWasCreated(){
        viewModelScope.launch(Dispatchers.IO) {
            _isProfileCreated.postValue(
                sharedPrefUseCase.getCheckupTerms()
            )
        }
    }
}