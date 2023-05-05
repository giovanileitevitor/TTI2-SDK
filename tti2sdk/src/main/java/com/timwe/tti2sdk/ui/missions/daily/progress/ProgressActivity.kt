package com.timwe.tti2sdk.ui.missions.daily.progress

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivityProgressBinding
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.missions.daily.DailyViewModel
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgressActivity: AppCompatActivity() {

    private lateinit var binding : ActivityProgressBinding
    private val viewModel: DailyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
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

        binding.txtTitleDailyCheckup.text = mission.additionalProperties.missionMenuTitle ?: "error"
        binding.txtDescriptionDailyCheckup.text = mission.description ?: "description error"
        mission.additionalProperties.missionActionUrl1.let{
            Glide.with(this)
                .load(it)
                .into(binding.imgMainDailyCheckup)
        }

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

    private fun setupObservers(){
        viewModel.loading.observe(this) {
            if (it) {
                binding.loadingBox.visibility = View.VISIBLE
                //binding.blankContainer.visibility = View.VISIBLE
            } else {
                binding.loadingBox.visibility = View.GONE
                //binding.blankContainer.visibility = View.GONE
            }
        }

        viewModel.error.observe(this) {
            DialogError(
                this@ProgressActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError {
                    override fun reloadClickListener() {

                    }
                }
            )
        }
    }
}