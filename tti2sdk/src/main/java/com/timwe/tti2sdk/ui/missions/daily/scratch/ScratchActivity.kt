package com.timwe.tti2sdk.ui.missions.daily.scratch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivityScratchBinding
import com.timwe.tti2sdk.ui.missions.daily.DailyViewModel
import com.timwe.utils.Utils
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScratchActivity: AppCompatActivity() {

    private lateinit var binding : ActivityScratchBinding
    private val viewModel: DailyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScratchBinding.inflate(layoutInflater)
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

        binding.txtTitleDailyCheckup.text = mission.additionalProperties.missionMenuTitle ?: "error"
        binding.txtDescriptionDailyCheckup.text = mission.description ?: "description error"


        Utils.showLog("SDK", "mission: ${mission.missionId}")
    }

    private fun setupListeners(){
        binding.btnBackDailyCheckup.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.btnDoItLater.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }
}