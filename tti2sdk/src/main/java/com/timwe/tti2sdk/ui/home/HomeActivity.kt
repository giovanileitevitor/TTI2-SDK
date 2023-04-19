package com.timwe.tti2sdk.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.rive.runtime.kotlin.RiveArtboardRenderer
import app.rive.runtime.kotlin.core.*
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivityHomeBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.board.LeaderBoardActivity
import com.timwe.tti2sdk.ui.destinations.DestinationActivity
import com.timwe.tti2sdk.ui.helpwebview.HelpWebViewActivity
import com.timwe.tti2sdk.ui.missions.MissionsActivity
import com.timwe.tti2sdk.ui.onboarding.OnBoardingActivity
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeActivity: AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding : ActivityHomeBinding
    private var usingSystemBackStack = false
    private val mapView by lazy(LazyThreadSafetyMode.NONE) { binding.map }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Rive.init(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        setupElements()
        setupObservers()
        setupListeners()
    }

    private fun setupElements(){
        setupRive()
        viewModel.startLoading()
        viewModel.getAvatarStatus()
    }

    private fun setupObservers(){
        viewModel.loading.observe(this) {
            if (it) {
                binding.loadingBox.visibility = View.VISIBLE
            } else {
                binding.loadingBox.visibility = View.GONE
            }
        }

        viewModel.avatarStatus.observe(this){
            binding.containerStatusBoard.bringToFront()
            binding.nameAvatar.text = it.avatarName
            binding.teamAvatar.text = it.avatarTeam
            binding.tierAvatar.text = it.avatarTier
            binding.progressJourney.text = it.avatarPercentual.toString() + "%"
            binding.valueJourney.text = it.kmPercorrido.toString() + "km"
        }

        viewModel.startRiveListener.observe(this){
            if(it){
                riveListeners()
            }
        }

        viewModel.itemClicked.observe(this){
            if(it.isValidButton){
                val intent = Intent(this, DestinationActivity::class.java)
                intent.putExtra("DESTINATION_ID", it.buttonName)
                startActivity(intent)
            }
        }

    }

    private fun setupListeners(){
        binding.btnCloseSdk.onDebouncedListener{
            Toast.makeText(this, "Go out SDK", Toast.LENGTH_SHORT).show()
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnHelp.onDebouncedListener {
            if(binding.menuTop.visibility == View.VISIBLE){
                binding.menuTop.visibility = View.GONE
                binding.containerStatusBoard.visibility = View.VISIBLE
            }else{
                binding.menuTop.visibility = View.VISIBLE
                binding.menuTop.bringToFront()
                binding.containerStatusBoard.visibility = View.GONE
            }
        }

        binding.itemMenuGameHelp.onDebouncedListener {
            binding.menuTop.visibility = View.GONE
            val intent = Intent(this, HelpWebViewActivity::class.java)
            startActivity(intent)
        }

        binding.itemMenuReplay.onDebouncedListener {
            binding.menuTop.visibility = View.GONE
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
        }

        binding.iconBoard.onDebouncedListener {
            val intent = Intent(this, LeaderBoardActivity::class.java)
            startActivity(intent)
        }

        binding.containerStatusBoard.onDebouncedListener {
            val intent = Intent(this, AvatarActivity::class.java)
            startActivity(intent)
        }

        binding.iconMissions.onDebouncedListener {
            val intent = Intent(this, MissionsActivity::class.java)
            startActivity(intent)
        }

        binding.iconPrizes.onDebouncedListener {
            val intent = Intent(this, PrizesActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupRive() {
        mapView.setRiveResource(
            resId = R.raw.map_main_design_03,
            autoplay = true,
            fit = Fit.FILL,
            alignment = Alignment.CENTER,
            loop = Loop.LOOP,
            stateMachineName = "Game_Progress_Machine",
            artboardName = "Game_Board"
        )

        viewModel.startRiveListener()
    }

    private fun riveListeners(){

        val listener = object : RiveArtboardRenderer.Listener {
            override fun notifyLoop(animation: PlayableInstance) {
                val a = 10
            }

            override fun notifyPause(animation: PlayableInstance) {
                val b = 10
            }

            override fun notifyPlay(animation: PlayableInstance) {
                val position = (animation as NativeObject).cppPointer
                Log.i("SDK", "Position: ${position.toString()}")
            }

            override fun notifyStateChanged(stateMachineName: String, stateName: String) {
                Log.i("SDK", "StatemachineName: $stateMachineName \n StateName: $stateName")
                viewModel.processItemClicked(itemClicked = stateName)
            }

            override fun notifyStop(animation: PlayableInstance) {
                val f = 10
            }
        }

        mapView.registerListener(listener)

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (usingSystemBackStack) {
            if(supportFragmentManager.fragments.size == 2){
                usingSystemBackStack = false
            }

        } else {
            Toast.makeText(this, "Go out SDK", Toast.LENGTH_SHORT).show()
            onBackPressedDispatcher.onBackPressed()
        }
    }

}