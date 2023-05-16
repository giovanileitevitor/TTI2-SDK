package com.timwe.tti2sdk.ui.missions.daily.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivityQuizBinding
import com.timwe.tti2sdk.ui.missions.daily.DailyViewModel
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizActivity(): AppCompatActivity() {

    private lateinit var binding : ActivityQuizBinding
    private val viewModel: DailyViewModel by viewModel()
    private var groupMissionId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
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
        binding.btnBackQuiz.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    private fun setupObservers(){

    }

}