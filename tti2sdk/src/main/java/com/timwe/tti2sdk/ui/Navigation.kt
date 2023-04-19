package com.timwe.tti2sdk.ui

import android.os.Bundle
import com.timwe.tti2sdk.ui.avatar.fragments.ClothesFragment
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment
import com.timwe.tti2sdk.ui.avatar.fragments.RideFragment
import com.timwe.tti2sdk.ui.avatar.fragments.ShoesFragment
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import com.timwe.tti2sdk.ui.board.fragments.AllTimeFragment
import com.timwe.tti2sdk.ui.board.fragments.MonthFragment
import com.timwe.tti2sdk.ui.board.fragments.TodayFragment
import com.timwe.tti2sdk.ui.board.fragments.WeekFragment
import com.timwe.tti2sdk.ui.prizes.fragments.AvailableFragment
import com.timwe.tti2sdk.ui.prizes.fragments.HistoryFragment

class Navigation {

    companion object {

        fun getFragmentFromFragmentId(fragmentId: FragmentId, bundle: Bundle? = null): BaseFragment {
            var fragment: BaseFragment? = null
            when (fragmentId) {
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
                FragmentId.FRAG_AVALIABLE -> {
                    print("AVALIABLE")
                    fragment = AvailableFragment.newInstance()
                }
                FragmentId.FRAG_HISTORY -> {
                    print("HISTORY")
                    fragment = HistoryFragment.newInstance()
                }

//                FragmentId.FRAG_ALL_TIME -> {
//                    print("ALL TIME")
//                    fragment = AllTimeFragment.newInstance()
//                }
//                FragmentId.FRAG_TODAY -> {
//                    print("TODAY")
//                    fragment = TodayFragment.newInstance()
//                }
//                FragmentId.FRAG_WEEK -> {
//                    print("WEEK")
//                    fragment = WeekFragment.newInstance()
//                }
//                FragmentId.FRAG_MONTH -> {
//                    print("MONTH")
//                    fragment = MonthFragment.newInstance()
//                }

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