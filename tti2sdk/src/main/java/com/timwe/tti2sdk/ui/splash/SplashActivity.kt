package com.timwe.tti2sdk.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivitySplashBinding
import com.timwe.tti2sdk.ui.home.HomeActivity
import com.timwe.tti2sdk.ui.onboarding.OnBoardingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity(): AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
        setupListeners()
    }

    private fun setupView(){
        viewModel.start()
        viewModel.getUrls()

        val version = com.timwe.tti2sdk.BuildConfig.SDK_VERSION_CODE
        binding.labelVersion.text = getString(R.string.versionLabel, version)
        binding.labelVersion.bringToFront()
        binding.labelVersion.visibility = View.GONE
    }

    private fun setupObservers(){
        viewModel.next.observe(this, Observer { it->
            if(it){
                val intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        viewModel.hasUrls.observe(this, Observer{

        })
    }

    private fun setupListeners(){

    }

}