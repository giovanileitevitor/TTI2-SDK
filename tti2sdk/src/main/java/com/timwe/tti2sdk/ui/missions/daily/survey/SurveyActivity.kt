package com.timwe.tti2sdk.ui.missions.daily.survey

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivitySurveyBinding
import com.timwe.tti2sdk.ui.BaseActivity
import com.timwe.tti2sdk.ui.missions.daily.DailyViewModel
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class SurveyActivity(): BaseActivity() {

    private lateinit var binding : ActivitySurveyBinding
    private val viewModel: DailyViewModel by viewModel()
    private var groupMissionId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
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
        finish()
    }

    private fun setupView(){
        val gson = Gson()
        val dataReceivedJson = intent.getStringExtra("MISSION")
        val mission = gson.fromJson(dataReceivedJson, Mission2::class.java)
    }

    private fun setupListeners(){
        binding.btnBackSurvey.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    private fun setupObservers(){

    }

}