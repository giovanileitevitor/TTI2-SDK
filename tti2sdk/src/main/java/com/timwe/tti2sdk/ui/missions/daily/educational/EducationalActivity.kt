package com.timwe.tti2sdk.ui.missions.daily.educational

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivityEducationalBinding
import com.timwe.tti2sdk.databinding.DialogCompletedBinding
import com.timwe.utils.Utils
import com.timwe.utils.onDebouncedListener

class EducationalActivity(): AppCompatActivity() {

    private lateinit var binding : ActivityEducationalBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }

    private fun setupView(){
        val gson = Gson()
        val dataReceivedJson = intent.getStringExtra("MISSION")
        val mission = gson.fromJson(dataReceivedJson, Mission2::class.java)

        binding.txtDailyCheckupTitle.text = mission.category ?: "error"
        setWebViewClient(binding.webviewEducational)
        mission.educationalCards.educationalCards[0].url.let {
            binding.webviewEducational.loadUrl(it)
        }

    }

    private fun setupListeners(){
        binding.btnBackDailyCheckup.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.btnCompleteEducational.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    private fun setWebViewClient(webView: WebView?){
        webView?.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.loadingBox.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.loadingBox.visibility = View.GONE
            }
        }
    }

    private fun showCompletedDialog(){
        val completedDialog = AlertDialog.Builder(this).create()
        val bind: DialogCompletedBinding = DialogCompletedBinding.inflate(LayoutInflater.from(this))
        completedDialog.apply {
            setView(bind.root)
            setCancelable(false)
        }.show()


    }
}