package com.timwe.tti2sdk.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.timwe.init.UserProfile
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivitySplashBinding
import com.timwe.tti2sdk.di.MyApplication
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.home.HomeActivity
import com.timwe.tti2sdk.ui.onboarding.OnBoardingActivity
import com.timwe.utils.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity: AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
        //viewModel.sendEvent(eventType = EventType.SDK_LOGIN)
    }

    private fun setupView(){
        val userProfile = intent.getSerializableExtra("USER_PROFILE_KEY") as UserProfile
        Utils.showLog("SDK", "setStartParams userProfile: $userProfile")
        val isDebuggable = intent.getSerializableExtra("IS_DEBUGGABLE") as Boolean
        Utils.showLog("SDK", "setStartParams isDebuggable: $isDebuggable")

        MyApplication.instance?.isDebug = isDebuggable
        Utils.showLog("SDK", "getDebugable: ${MyApplication.instance?.isDebug}")
        MyApplication.instance?.userProfile = userProfile

        viewModel.saveDataFromMainApp(avatarProfile = userProfile, isDebugable = isDebuggable)
        viewModel.getUrlsAndToken()

        val version = com.timwe.tti2sdk.BuildConfig.SDK_VERSION_CODE
        binding.labelVersion.text = getString(R.string.versionLabel, version)
        binding.labelVersion.bringToFront()
    }

    private fun setupObservers(){
        viewModel.next.observe(this) { it ->
            if (it.status) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.loading.observe(this) {
            if(it) {
                binding.progessLayout.container.visibility = View.VISIBLE
                binding.labelProgress.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(this) {
            DialogError(
                this@SplashActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError {
                    override fun reloadClickListener() {
                        viewModel.getUrlsAndToken()
                    }
                }
            )
        }

        viewModel.tokenReceived.observe(this){ tokenReceived ->
            if(!tokenReceived.isNullOrEmpty()){
                MyApplication.instance?.token = tokenReceived
                Utils.showLog("SDK", "Token received: $tokenReceived")
            }
        }

    }

}