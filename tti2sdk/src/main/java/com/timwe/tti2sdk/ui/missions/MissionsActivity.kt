package com.timwe.tti2sdk.ui.missions

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.AdventureMissions
import com.timwe.tti2sdk.data.entity.BoosterMissions
import com.timwe.tti2sdk.data.entity.DailyMissions
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.databinding.ActivityMissionsBinding
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.missions.Constants.DAILY
import com.timwe.tti2sdk.ui.missions.Constants.DAILY_CHECKUP
import com.timwe.tti2sdk.ui.missions.Constants.DAILY_TIER
import com.timwe.tti2sdk.ui.missions.Constants.EDUCATIONAL
import com.timwe.tti2sdk.ui.missions.Constants.PROGRESS
import com.timwe.tti2sdk.ui.missions.Constants.QUIZ
import com.timwe.tti2sdk.ui.missions.Constants.REDIRECT
import com.timwe.tti2sdk.ui.missions.Constants.SCRATCH
import com.timwe.tti2sdk.ui.missions.Constants.SURVEY
import com.timwe.tti2sdk.ui.missions.Constants.TARGETED
import com.timwe.tti2sdk.ui.missions.daily.educational.EducationalActivity
import com.timwe.tti2sdk.ui.missions.daily.progress.ProgressActivity
import com.timwe.tti2sdk.ui.missions.daily.quiz.QuizActivity
import com.timwe.tti2sdk.ui.missions.daily.scratch.ScratchActivity
import com.timwe.tti2sdk.ui.missions.daily.survey.SurveyActivity
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MissionsActivity: AppCompatActivity() {

    private lateinit var binding : ActivityMissionsBinding
    private val viewModel: MissionsViewModel by viewModel()
    private lateinit var dailyMissionAdapter: DailyMissionAdapter
    private lateinit var adventureMissionsAdapter: DailyMissionAdapter
    private lateinit var boosterMissionsAdapter: DailyMissionAdapter
    private var tier : String = ""
    private var groupIdTemp : Long = 0L

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

        binding.containerRefreshAdventure.onDebouncedListener {
            viewModel.skipAdventure(groupId = groupIdTemp)
        }
    }

    private fun setupObservers(){
        viewModel.tierType.observe(this){ tierType ->
            if(!tierType.isNullOrEmpty()){
                tier = tierType
            } else{
                viewModel.getMissions()
            }
        }

        viewModel.skipResult.observe(this){ skippedAdventureResult ->
            if(skippedAdventureResult.skipped){
                binding.containerRefreshAdventure.visibility = View.GONE
            }else{
                binding.containerRefreshAdventure.visibility = View.VISIBLE
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

        viewModel.loading.observe(this) {
            if (it) {
                binding.loadingBox.visibility = View.VISIBLE
                binding.blankContainer.visibility = View.VISIBLE
            } else {
                binding.loadingBox.visibility = View.GONE
                binding.blankContainer.visibility = View.GONE
            }
        }

        viewModel.error.observe(this) {
            DialogError(
                this@MissionsActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError {
                    override fun reloadClickListener() {
                        viewModel.getMissions()
                    }
                }
            )
        }
    }

    private fun setupDailyMissionsRV(missions: DailyMissions){
        binding.rvDailyMissions.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dailyMissionAdapter = DailyMissionAdapter(
            context = this,
            data = missions.dailyMissions,
            tier = tier,
            mGlide = Glide.with(this),
            singleClickDaily
        )
        binding.rvDailyMissions.adapter = dailyMissionAdapter
        binding.txtDailySubtitle.text = missions.titleMissions
    }

    private val singleClickDaily = { mission: Mission2 ->
        when(mission.missionType){
            DAILY_CHECKUP -> {
                when(mission.missionSubType){
                    PROGRESS -> {
                        //GO TO PROGRESS SCREEN
                        val intent = Intent(this, ProgressActivity::class.java)
                        val gson = Gson()
                        val dadosGson = gson.toJson(mission)
                        intent.putExtra("MISSION", dadosGson)
                        startActivity(intent)
                    }
                    SCRATCH -> {
                        //GO TO SCRATCH SCREEN
                        val intent = Intent(this, ScratchActivity::class.java)
                        val gson = Gson()
                        val dadosGson = gson.toJson(mission)
                        intent.putExtra("MISSION", dadosGson)
                        startActivity(intent)
                    }
                    else -> {
                        //CENÁRIO DE FALHA
                        Toast.makeText(this, "Unknown scenario", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            DAILY_TIER -> {
                when(mission.missionSubType){
                    QUIZ -> {
                        //GO TO QUIZ SCREEN
                        val intent = Intent(this, QuizActivity::class.java)
                        val gson = Gson()
                        val dadosGson = gson.toJson(mission)
                        intent.putExtra("MISSION", dadosGson)
                        startActivity(intent)
                    }
                    EDUCATIONAL -> {
                        //GO TO SCRATCH SCREEN
                        val intent = Intent(this, EducationalActivity::class.java)
                        val gson = Gson()
                        val dadosGson = gson.toJson(mission)
                        intent.putExtra("MISSION", dadosGson)
                        startActivity(intent)
                    }
                    SURVEY -> {
                        //GO TO SURVEY SCREEN
                        val intent = Intent(this, SurveyActivity::class.java)
                        val gson = Gson()
                        val dadosGson = gson.toJson(mission)
                        intent.putExtra("MISSION", dadosGson)
                        startActivity(intent)
                    }
                    REDIRECT -> {
                        notAvailable()
                    }
                    else -> {
                        notAvailableElse()
                    }
                }
            }

            DAILY -> {
                // nao tem tela de tratamento
                Toast.makeText(this, this.getString(R.string.dailyMissionCompleted), Toast.LENGTH_SHORT).show()
            }

            else -> {
                notAvailableElse()
            }
        }
    }

    private fun setupAdventureMissionsRV(adventures: AdventureMissions){
        binding.rvAdventureMissions.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adventureMissionsAdapter = DailyMissionAdapter(
            context = this,
            data = adventures.adventureMissions,
            tier = tier,
            mGlide = Glide.with(this),
            singleClickAdventure
        )
        binding.rvAdventureMissions.adapter = adventureMissionsAdapter
        binding.txtAdventureSubtitle.text = adventures.titleAdventure
        groupIdTemp = adventures.groupId
        binding.containerRefreshAdventure.visibility = if(adventures.skipEnabled == true) View.VISIBLE else View.GONE
    }

    private val singleClickAdventure = { mission: Mission2 ->
        when(mission.missionType){
            TARGETED -> {
                when(mission.missionSubType){
                    QUIZ -> {
                        //GO TO QUIZ SCREEN
                        val intent = Intent(this, QuizActivity::class.java)
                        val gson = Gson()
                        val dadosGson = gson.toJson(mission)
                        intent.putExtra("MISSION", dadosGson)
                        startActivity(intent)
                    }
                    EDUCATIONAL -> {
                        //GO TO SCRATCH SCREEN
                        val intent = Intent(this, EducationalActivity::class.java)
                        val gson = Gson()
                        val dadosGson = gson.toJson(mission)
                        intent.putExtra("MISSION", dadosGson)
                        startActivity(intent)
                    }
                    SURVEY -> {
                        //GO TO SURVEY SCREEN
                        val intent = Intent(this, SurveyActivity::class.java)
                        val gson = Gson()
                        val dadosGson = gson.toJson(mission)
                        intent.putExtra("MISSION", dadosGson)
                        startActivity(intent)
                    }
                    else -> {
                        val a = 210
                    }
                }
            }
            else -> {

            }
        }
    }

    private fun setupBoosterMissions(boosters: BoosterMissions){
        binding.rvBoosterMissions.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        boosterMissionsAdapter = DailyMissionAdapter(
            context = this,
            data = boosters.boosterMissions,
            tier = tier,
            mGlide = Glide.with(this),
            singleClickBooster
        )
        binding.rvBoosterMissions.adapter = boosterMissionsAdapter
        binding.txtBoosterSubtitle.text = boosters.titleBooster
    }

    private val singleClickBooster = { mission: Mission2 ->
        notAvailable()
    }

//    private fun processMissionCLicked(mission: Mission2){
//        when(mission.missionType){
//            DAILY_CHECKUP -> {
//                val intent = Intent(this, DailyCheckupActivity::class.java)
//                val gson = Gson()
//                val dadosGson = gson.toJson(mission)
//                intent.putExtra("MISSION", dadosGson)
//                startActivity(intent)
//            }
//            QUIZ -> { notAvailable()
//            }
//            SURVEY -> { notAvailable()
//            }
//            EDUCATIONAL -> { notAvailable()
//            }
//            SCRATCH -> { notAvailable()
//            }
//            CLICK -> { notAvailable()
//            }
//            PROGRESS -> { notAvailable()
//            }
//            VIDEO -> { notAvailable()
//            }
//            REDIRECT -> { notAvailable()
//            }
//            else -> { notAvailableElse()}
//        }
//    }

    private fun notAvailable(){
        Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
    }

    private fun notAvailableElse(){
        Toast.makeText(this, "Under development - else", Toast.LENGTH_SHORT).show()
    }

}