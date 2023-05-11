package com.timwe.tti2sdk.ui.missions.daily.progress

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivityProgressBinding
import com.timwe.tti2sdk.ui.missions.daily.DailyViewModel
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgressActivity: AppCompatActivity() {

    private lateinit var binding : ActivityProgressBinding
    private val viewModel: DailyViewModel by viewModel()
    private var groupMissionId: Long = 0L

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
        groupMissionId = mission.groupMissionId
        mission.additionalProperties.missionActionUrl1.let{
            binding.imgMainDailyCheckup.setAnimationFromUrl(it)
            binding.imgMainDailyCheckup.playAnimation()
        }
    }

    private fun setupListeners(){
        binding.btnBackDailyCheckup.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.btnDoItLater.onDebouncedListener {
            finish()
        }

        binding.btnConfirmAction.onDebouncedListener {
            if(groupMissionId != 0L){
                viewModel.setProgressMissionToCompleted(groupMissionId = groupMissionId)
            }else{
                Toast.makeText(this, "Error... \n Please reload this screen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers(){
        viewModel.progressMissionCompleted.observe(this){ isProgressMissionCompleted ->
            if(isProgressMissionCompleted){
                showCompletedDialog()
            }
        }

        viewModel.loadingProgress.observe(this) {
            if (it) {
                binding.loadingBox.visibility = View.VISIBLE
            } else {
                binding.loadingBox.visibility = View.GONE
            }
        }

    }

    private fun showCompletedDialog(){
        binding.dialogCompleted.root.visibility = View.VISIBLE
        binding.dialogCompleted.btnCompleteEduc.onDebouncedListener {
            finish()
        }

    }
}