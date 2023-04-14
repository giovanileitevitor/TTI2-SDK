package com.timwe.tti2sdk.ui.board.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> AllTimeFragment.newInstance()
            1 -> TodayFragment.newInstance()
            2 -> WeekFragment.newInstance()
            3 -> MonthFragment.newInstance()
            else -> throw IllegalArgumentException(" Invalid tab position: $position")
        }
    }
}