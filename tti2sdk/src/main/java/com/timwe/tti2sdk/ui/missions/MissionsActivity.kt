package com.timwe.tti2sdk.ui.missions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.databinding.ActivityMissionsBinding
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MissionsActivity: AppCompatActivity() {

    private lateinit var binding : ActivityMissionsBinding
    private val viewModel: MissionsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
        viewModel.getMissions()
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun setupView(){

    }

    private fun setupListeners(){
        binding.btnBackMissions.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupObservers(){

    }

}