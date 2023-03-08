package com.timwe.tti2sdk.ui.avatar

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivityAvatarBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvatarActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAvatarBinding
    private val viewModel: AvatarViewModel by viewModel()
    private  val rotateElement : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_anim) }

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

    private fun setupView() = with(binding){

    }

    private fun setupListeners(){
        binding.btnBackAvatar.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnShareAvatar.setOnClickListener {

        }

        binding.btnSaveAvatar.setOnClickListener {

        }
        binding.btnRandomize.setOnClickListener{
            startingIconAnimation()
            //Do the action to randomize avatar
        }

    }

    private fun setupObservers(){

    }

    private fun startingIconAnimation() = with(binding){
        imgRandomize.startAnimation(rotateElement)
    }
}