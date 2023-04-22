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
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.helpwebview.HelpWebViewActivity
import com.timwe.tti2sdk.ui.onboarding.OnBoardingActivity
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.utils.formatToKm
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
        setupElements()
        setupObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupElements(){
        setupRive()
        viewModel.getProfileInfo()
    }

    private fun setupObservers(){
        viewModel.loading.observe(this) {
            if (it) {
                binding.loadingBox.visibility = View.VISIBLE
            } else {
                binding.loadingBox.visibility = View.GONE
            }
        }

        viewModel.profileInfo.observe(this){
            binding.containerStatusBoard.bringToFront()

            if(it.tierName == "gold"){
                binding.tierAvatar.setBackgroundResource(R.drawable.background_gold)

            }else if(it.tierName == "silver"){
                binding.tierAvatar.setBackgroundResource(R.drawable.background_silver)

            }else if(it.tierName == "bronze"){
                binding.tierAvatar.setBackgroundResource(R.drawable.background_bronze)

            }else{
                binding.tierAvatar.setBackgroundResource(R.drawable.background_gold)

            }

            binding.nameAvatar.text = it.userName
            binding.tierAvatar.text = it.tierName
            binding.progressJourney.text = "${((it.currentKms*100)/it.remainingKms)}%"
            binding.valueJourney.text = it.remainingKms.formatToKm()
        }

        viewModel.startRiveListener.observe(this){
            if(it){
                riveListeners()
            }
        }

        viewModel.itemClicked.observe(this){
            if(it.isValidButton){
                val intent = Intent(this, DestinationActivity::class.java)
                intent.putExtra("DESTINATION_ID", it.buttonName.toLong())
                startActivity(intent)
                finish()
            }
        }

        viewModel.error.observe(this, ){
            DialogError(
                this@HomeActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError{
                    override fun reloadClickListener() {
                        viewModel.getProfileInfo()
                    }
                }
            )
        }

    }

    private fun setupListeners(){

        binding.teamAvatar.onDebouncedListener{
            val intent = Intent(this, AvatarActivity::class.java)
            startActivity(intent)
        }

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

        binding.iconMissions.onDebouncedListener {
            Toast.makeText(this, "Under development", Toast.LENGTH_SHORT).show()
            //val intent = Intent(this, MissionsActivity::class.java)
            //startActivity(intent)
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
            override fun notifyLoop(animation: PlayableInstance) { }

            override fun notifyPause(animation: PlayableInstance) { }

            override fun notifyPlay(animation: PlayableInstance) { }

            override fun notifyStop(animation: PlayableInstance) { }

            override fun notifyStateChanged(stateMachineName: String, stateName: String) {
                Log.i("SDK", "StatemachineName: $stateMachineName \n StateName: $stateName")
                viewModel.processItemClicked(itemClicked = stateName)
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