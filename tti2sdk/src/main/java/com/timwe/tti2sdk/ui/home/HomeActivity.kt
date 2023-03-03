package com.timwe.tti2sdk.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivityHomeBinding
import com.timwe.tti2sdk.ui.Navigation
import com.timwe.tti2sdk.ui.fragments.BaseFragment

class HomeActivity: AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    var usingSystemBackStack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
    }

    private fun setupView(){
        val fragment: BaseFragment = Navigation.getFragmentFromFragmentId(Navigation.FragmentId.HOME)
        showNewFragment(fragment)
    }

    private fun setupListeners(){

    }

    fun showNewFragment(fragment: BaseFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.ttil_sdk_main_frame_layout, fragment)
        transaction.commit()
    }

    fun showNewFragmentAndAddToSystemBackStack(fragment: BaseFragment?) {
        usingSystemBackStack = true
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.ttil_sdk_main_frame_layout, fragment!!)
        transaction.commit()
    }

}