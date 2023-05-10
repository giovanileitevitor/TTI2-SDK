package com.timwe.tti2sdk.ui.missions.daily.educational

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivityEducationalBinding
import com.timwe.tti2sdk.databinding.DialogCompletedBinding
import com.timwe.tti2sdk.ui.missions.daily.DailyViewModel
import com.timwe.utils.Utils
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class EducationalActivity(): AppCompatActivity() {

    private lateinit var binding : ActivityEducationalBinding
    private val viewModel: DailyViewModel by viewModel()
    private var groupMissionId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.containerAction.visibility = View.GONE
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
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
        groupMissionId = mission.groupMissionId
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
            if(groupMissionId != 0L){
                viewModel.setEducationMissionToCompleted(groupMissionId = groupMissionId)
            }else{
                Toast.makeText(this, "Error... \n Please reload this screen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers(){
        viewModel.educationMissionCompleted.observe(this){ isEducMissionCompleted ->
            if(isEducMissionCompleted){
                showCompletedDialog()
            }
        }

        viewModel.loadingEduc.observe(this){
            if (it) {
                binding.loadingBox.visibility = View.VISIBLE
            } else {
                binding.loadingBox.visibility = View.GONE
            }
        }
    }

    private fun setWebViewClient(webView: WebView?){
        webView?.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.loadingBox.visibility = View.VISIBLE
                binding.containerAction.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.loadingBox.visibility = View.GONE
                binding.containerAction.visibility = View.VISIBLE
            }
        }
    }

    private fun showCompletedDialog(){
        binding.dialogEducCompleted.root.visibility = View.VISIBLE
        binding.dialogEducCompleted.btnCompleteEduc.onDebouncedListener {
            finish()
        }

    }
}