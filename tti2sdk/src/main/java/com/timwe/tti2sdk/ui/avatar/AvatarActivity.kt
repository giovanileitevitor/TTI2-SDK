package com.timwe.tti2sdk.ui.avatar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.databinding.ActivityAvatarBinding

class AvatarActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAvatarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

    }

    override fun onResume() {
        super.onResume()
        setupObservers()
        setupListeners()
    }

    private fun setupView(){

    }

    private fun setupListeners(){

    }

    private fun setupObservers(){

    }
}