package com.timwe.tti2sdk.ui

import android.os.Bundle
import com.timwe.tti2sdk.ui.fragments.BaseFragment
import com.timwe.tti2sdk.ui.fragments.HomeFragment
import com.timwe.tti2sdk.ui.fragments.MissionsFragment

class Navigation {

    enum class FragmentId {
        HOME, MISSIONS
    }

    companion object {

        fun getFragmentFromFragmentId(fragmentId: FragmentId,  bundle: Bundle? = null): BaseFragment {
            var fragment: BaseFragment? = null
            when (fragmentId) {
                FragmentId.HOME ->{
                    print("HOME")
                    fragment = HomeFragment.newInstance()
                }
                FragmentId.MISSIONS -> {
                    print("MISSIONS")
                    fragment = MissionsFragment.newInstance()
                }
                else -> {
                    print("Fragment Id not exists")
                    throw Exception("Fragment Id not exists")
                }
            }
            return fragment
        }

    }


}