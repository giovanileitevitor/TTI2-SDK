package com.timwe.tti2sdk.ui.team

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.databinding.ActivityTeamBinding
import com.timwe.utils.onDebouncedListener

class TeamActivity: AppCompatActivity() {

    private lateinit var binding : ActivityTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun setupView(){

    }

    private fun setupListeners(){
        binding.btnBackTeam.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupObservers(){

    }

}