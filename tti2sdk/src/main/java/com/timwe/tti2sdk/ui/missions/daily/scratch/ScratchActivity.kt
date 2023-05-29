package com.timwe.tti2sdk.ui.missions.daily.scratch

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivityScratchBinding
import com.timwe.tti2sdk.ui.customViews.ScratchCard
import com.timwe.tti2sdk.ui.missions.daily.DailyViewModel
import com.timwe.utils.GenericListener
import com.timwe.utils.Utils
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.AccessController.getContext


class ScratchActivity: AppCompatActivity() {

    private lateinit var binding : ActivityScratchBinding
    private val viewModel: DailyViewModel by viewModel()
    private val listener: GenericListener<Void>? = null
    private val scratchCard: ScratchCard? = null
    private var percentage = 1.0


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

        //binding.imgMainDailyCheckup.setScratchDrawable(mission.additionalProperties.missionActionUrl1)

        percentage = 0.1

        updateViews(
            imageUrl1 = mission.additionalProperties.missionActionUrl1,
            imageUrl2 = mission.additionalProperties.missionActionUrl2
        )

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

        binding.scratchCard.setOnScratchListener( object: ScratchCard.OnScratchListener{
            override fun onStartedScratch(visiblePercent: Float) {
                Utils.showLog("SDK", "Scratch percentage: ${visiblePercent.toString()}")
            }

            override fun onScratch(scratchCard: ScratchCard?, visiblePercent: Float) {
                if(listener != null && visiblePercent >= percentage){
                    listener.onEvent(null)
                }
            }
        })
    }

    private fun updateViews(imageUrl1: String, imageUrl2: String) {
        Utils.getDrawableFromUrl(this, imageUrl1) { item ->
            try {
                Utils.showLog("SDK", "updateViews getDrawableFromUrl: runOnUiAfterAsync$item")
                binding.scratchCard.setScratchDrawable(item)
                binding.scratchCard.visibility = View.VISIBLE
                binding.mainImg.visibility = View.VISIBLE
                //showImageBG(imageUrl1)

            } catch (e: Exception) {
                Utils.showLog("SDK", "updateViews getDrawableFromUrl: runOnUiAfterAsync$e")
                e.printStackTrace()
            }
        }
        Utils.setImageViewFromUrl(this, binding.mainImg, imageUrl2)
    }

    private fun showImageBG(imageUrl: String){
        Glide.with(this)
            .load(imageUrl)
            .into(binding.mainImg)
    }

    private fun showCompletedScratch(){
        binding.dialogScratchCompleted.root.visibility = View.VISIBLE
        binding.dialogScratchCompleted.btnCompleteEduc.onDebouncedListener {
            finish()
        }

    }

}