package com.timwe.tti2sdk.ui.missions

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.data.entity.AdventureMissions
import com.timwe.tti2sdk.data.entity.BoosterMissions
import com.timwe.tti2sdk.data.entity.DailyMissions
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivityMissionsBinding
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.missions.dailycheckups.DailyMissionAdapter
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MissionsActivity: AppCompatActivity() {

    private lateinit var binding : ActivityMissionsBinding
    private val viewModel: MissionsViewModel by viewModel()
    private lateinit var dailyMissionAdapter: DailyMissionAdapter
    private lateinit var adventureMissionsAdapter: DailyMissionAdapter
    private lateinit var boosterMissionsAdapter: DailyMissionAdapter
    private var tier : String = ""

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
        viewModel.getTierType()
    }

    private fun setupListeners(){
        binding.btnBackMissions.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    private fun setupObservers(){
        viewModel.tierType.observe(this){ tierType ->
            if(!tierType.isNullOrEmpty()){
                viewModel.getMissions()
                tier = tierType
            } else{
                viewModel.getMissions()
            }
        }

        viewModel.dailyMissions.observe(this){ dailyMissions ->
            setupDailyMissionsRV(missions = dailyMissions)
        }

        viewModel.adventureMissions.observe(this){ adventureMissions ->
            setupAdventureMissionsRV(adventures = adventureMissions)
        }

        viewModel.boosterMissions.observe(this){ boosterMissions ->
            setupBoosterMissions(boosters = boosterMissions)
        }

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
                        viewModel.getMissions()
                    }
                }
            )
        })
    }

    private fun setupDailyMissionsRV(missions: DailyMissions){
        binding.rvDailyMissions.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dailyMissionAdapter = DailyMissionAdapter(
            context = this,
            data = missions.dailyMissions,
            tier = tier,
            mGlide = Glide.with(this),
            singleClick
        )
        binding.rvDailyMissions.adapter = dailyMissionAdapter
        binding.txtDailySubtitle.text = missions.titleMissions
    }

    private val singleClick = { mission: Mission2 ->
        Toast.makeText(this, "Under Development: ${mission.missionId.toString()}", Toast.LENGTH_SHORT).show()
    }

    private fun setupAdventureMissionsRV(adventures: AdventureMissions){
        binding.rvAdventureMissions.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adventureMissionsAdapter = DailyMissionAdapter(
            context = this,
            data = adventures.adventureMissions,
            tier = tier,
            mGlide = Glide.with(this),
            singleClick
        )
        binding.rvAdventureMissions.adapter = adventureMissionsAdapter
        binding.txtAdventureSubtitle.text = adventures.titleAdventure
    }

    private fun setupBoosterMissions(boosters: BoosterMissions){
        binding.rvBoosterMissions.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        boosterMissionsAdapter = DailyMissionAdapter(
            context = this,
            data = boosters.boosterMissions,
            tier = tier,
            mGlide = Glide.with(this),
            singleClick
        )
        binding.rvBoosterMissions.adapter = boosterMissionsAdapter
        binding.txtBoosterSubtitle.text = boosters.titleBooster
    }

}