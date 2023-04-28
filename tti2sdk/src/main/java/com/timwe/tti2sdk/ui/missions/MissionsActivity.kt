package com.timwe.tti2sdk.ui.missions

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.databinding.ActivityMissionsBinding
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.missions.dailycheckups.DailyMissionAdapter
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MissionsActivity: AppCompatActivity() {

    private lateinit var binding : ActivityMissionsBinding
    private val viewModel: MissionsViewModel by viewModel()
    private lateinit var dailyMissionAdapter: DailyMissionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setupView()
        setupListeners()
        setupObservers()
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }

    private fun setupView(){
        viewModel.getDailyMissions()
        viewModel.getAdventureMissions()
        viewModel.getBoosterMissions()
        //viewModel.getMissions()
    }

    private fun setupListeners(){
        binding.btnBackMissions.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    private fun setupObservers(){
        viewModel.dailyMissions.observe(this){ dailyMissions ->
            setupDailyMissionsRV(dailyMissions = dailyMissions)
        }

//        viewModel.missions.observe(this, Observer{ missions ->
//            if(missions != null){
//                setupDailyCheckupRV(missions = viewModel.getMockedMissions(3))
//            }else{
//                //Do not show daily missions label
//            }
//        })

        viewModel.loading.observe(this, Observer{
            if(it){
                binding.loadingBox.visibility = View.VISIBLE
                binding.blankContainer.visibility = View.VISIBLE
            }else{
                binding.loadingBox.visibility = View.GONE
                binding.blankContainer.visibility = View.GONE
            }
        })

        viewModel.error.observe(this, Observer{
            DialogError(
                this@MissionsActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError{
                    override fun reloadClickListener() {
                        viewModel.getDailyMissions()
                    }
                }
            )
        })
    }

    private fun setupDailyMissionsRV(dailyMissions: List<Mission>){
        binding.rvDailyMissions.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dailyMissionAdapter = DailyMissionAdapter(
            context = this,
            data = dailyMissions,
            mGlide = Glide.with(this),
            singleClick
        )
        binding.rvDailyMissions.adapter = dailyMissionAdapter

    }

    private val singleClick = { mission: Mission ->
        Toast.makeText(this, "Under Development: ${mission.id.toString()}", Toast.LENGTH_SHORT).show()
    }


//    private fun setupDailyCheckupRV(missions: List<Mission>){
//        binding.rvDailyCheckup.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        dailyCheckupAdapter = DailyCheckupAdapter(
//            context = this,
//            data = missions,
//            mGlide = Glide.with(this),
//            singleClick
//        )
//        binding.rvDailyCheckup.adapter = dailyCheckupAdapter
//
//    }
//
//    private val singleClick = { mission: Mission ->
//        Toast.makeText(this, "Mission: ${mission.id.toString()}", Toast.LENGTH_SHORT).show()
//    }

}