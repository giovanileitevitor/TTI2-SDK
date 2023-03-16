package com.timwe.tti2sdk.ui.prizes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.databinding.ActivityPrizesBinding
import com.timwe.utils.onDebouncedListener

class PrizesActivity: AppCompatActivity() {

    private lateinit var binding : ActivityPrizesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrizesBinding.inflate(layoutInflater)
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
        binding.btnBackPrizes.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupObservers(){

    }
}