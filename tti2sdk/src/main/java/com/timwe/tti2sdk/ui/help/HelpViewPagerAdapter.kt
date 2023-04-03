package com.timwe.tti2sdk.ui.help

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HelpViewPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()

    override fun getCount() = mFragmentList.size

    override fun getItem(position: Int) = mFragmentList[position]

    fun addFragment(fragment:Fragment)
    {
        mFragmentList.add(fragment)
    }
}