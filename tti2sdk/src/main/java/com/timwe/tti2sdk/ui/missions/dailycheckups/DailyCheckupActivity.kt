package com.timwe.tti2sdk.ui.missions.dailycheckups

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.databinding.ActivityDailycheckupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DailyCheckupActivity: AppCompatActivity() {

    private lateinit var binding : ActivityDailycheckupBinding
    private val viewModel: DailyCheckupViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailycheckupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
    }

    private fun setupView(){

    }

    private fun setupListeners(){

    }

    private fun setupObservers(){

    }
}