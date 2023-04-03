package com.timwe.tti2sdk.ui.help

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.databinding.ActivityHelpBinding
import com.timwe.utils.onDebouncedListener
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpActivity: AppCompatActivity() {

    private val viewModel: HelpViewModel by viewModel()
    private lateinit var binding : ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        val adapter = HelpViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(HelpMissionsFragment())
        adapter.addFragment(HelpPrizesFragment())
        adapter.addFragment(HelpRupiahFragment())
        adapter.addFragment(HelpEventsFragment())
        adapter.addFragment(HelpTierRewardsFragment())

        //binding.helpViewPager.adapter = adapter
        //binding.helpTabLayout.setupWithViewPager(binding.helpViewPager)
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onBackPressedDispatcher.onBackPressed()
    }

    private fun setupView(){

    }

    private fun setupListeners(){
        binding.btnBackHelp.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupObservers(){

    }

}