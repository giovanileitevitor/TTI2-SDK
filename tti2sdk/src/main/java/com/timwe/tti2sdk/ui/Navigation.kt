package com.timwe.tti2sdk.ui

import android.os.Bundle
import com.timwe.tti2sdk.ui.avatar.fragments.ClothesFragment
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment
import com.timwe.tti2sdk.ui.avatar.fragments.RideFragment
import com.timwe.tti2sdk.ui.avatar.fragments.ShoesFragment
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import com.timwe.tti2sdk.ui.home.fragments.HomeFragment

class Navigation {

    companion object {

        fun getFragmentFromFragmentId(fragmentId: FragmentId, bundle: Bundle? = null): BaseFragment {
            var fragment: BaseFragment? = null
            when (fragmentId) {
                FragmentId.FRAG_HOME -> {
                    print("HOME")
                    fragment = HomeFragment.newInstance()
                }
                FragmentId.FRAG_HEAD ->{
                    print("HEAD")
                    fragment = HeadFragment.newInstance()
                }
                FragmentId.FRAG_CLOTHES -> {
                    print("CLOTHES")
                    fragment = ClothesFragment.newInstance()
                }
                FragmentId.FRAG_SHOES -> {
                    print("SHOES")
                    fragment = ShoesFragment.newInstance()
                }
                FragmentId.FRAG_RIDE -> {
                    print("RIDE")
                    fragment = RideFragment.newInstance()
                }
                else -> {
                    print("Fragment Id not exists")
                    throw Exception("Fragment Id not exists")
                }
            }

            if (bundle != null) {
                fragment.arguments = bundle
            }

            return fragment
        }

    }


}