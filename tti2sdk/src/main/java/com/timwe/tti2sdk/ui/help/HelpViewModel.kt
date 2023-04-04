package com.timwe.tti2sdk.ui.help

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.HelpInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HelpViewModel(
    private val context: Context
): ViewModel() {

    private val _helpInfo = MutableLiveData<List<HelpInfo>>()
    val helpInfo: LiveData<List<HelpInfo>> get() = _helpInfo

    fun getHelpData(){
        viewModelScope.launch(Dispatchers.IO) {
            val helpInfoList = ArrayList<HelpInfo>()

            //Page 1
            helpInfoList.add(
                HelpInfo(
                    id = 1,
                    imageDrawable = R.drawable.help_missions_splash_image,
                    txtTitleHelp = context.getString(R.string.txt_help_titleMissions),
                    txtSubtitleHelp = context.getString(R.string.txt_help_descriptionMissions)
                )
            )
            //Page 2
            helpInfoList.add(
                HelpInfo(
                    id = 2,
                    imageDrawable = R.drawable.help_prizes_splash_image,
                    txtTitleHelp = context.getString(R.string.txt_help_titlePrizes),
                    txtSubtitleHelp = context.getString(R.string.txt_help_descriptionPrizes)
                )
            )

            //Page 3
            helpInfoList.add(
                HelpInfo(
                    id = 3,
                    imageDrawable = R.drawable.help_rupiah_splash_image,
                    txtTitleHelp = context.getString(R.string.txt_help_titleRupiahForKM),
                    txtSubtitleHelp = context.getString(R.string.txt_help_descriptionRupiahForKM)
                )
            )

            //Page 4
            helpInfoList.add(
                HelpInfo(
                    id = 4,
                    imageDrawable = R.drawable.help_events_splash_image,
                    txtTitleHelp = context.getString(R.string.txt_help_titleEvents),
                    txtSubtitleHelp = context.getString(R.string.txt_help_descriptionEvents)
                )
            )

            //Page 5
            helpInfoList.add(
                HelpInfo(
                    id = 5,
                    imageDrawable = R.drawable.help_tier_rewards_splash_image,
                    txtTitleHelp = context.getString(R.string.txt_help_titleTiers),
                    txtSubtitleHelp = context.getString(R.string.txt_help_descriptionTiers)
                )
            )
            delay(500)
            _helpInfo.postValue(helpInfoList)
        }
    }
}