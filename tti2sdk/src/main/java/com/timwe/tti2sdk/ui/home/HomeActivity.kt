package com.timwe.tti2sdk.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.rive.runtime.kotlin.RiveArtboardRenderer
import app.rive.runtime.kotlin.core.*
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivityHomeBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.onboarding.OnBoardingActivity
import com.timwe.tti2sdk.ui.missions.MissionsActivity
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.tti2sdk.ui.board.LeaderBoardActivity
import com.timwe.tti2sdk.ui.destinations.DestinationActivity
import com.timwe.tti2sdk.ui.helpwebview.HelpWebViewActivity
import com.timwe.utils.getDimensions
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeActivity: AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding : ActivityHomeBinding
    private var text: String = ""
    private var usingSystemBackStack = false
    private val mapView by lazy(LazyThreadSafetyMode.NONE) { binding.map }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setupView()
        setupListeners()
        setupObservers()
        setupElements()
    }

    private fun setupElements(){
        viewModel.startLoading()
        viewModel.getData()
    }

    private fun setupView(){
//        binding.mapContainer.apply {
//            overScrollMode = ScrollView.OVER_SCROLL_NEVER
//            //horizontalScrollbarThumbDrawable = getColor(R.color.parcial_transparent)
//            isHorizontalScrollBarEnabled = false
//        }

        mapView.setRiveResource(
            resId = R.raw.map_main_prod_03,
            autoplay = true,
            fit = Fit.FILL,
            alignment = Alignment.CENTER,
            loop = Loop.LOOP
        )

//        binding.map.bringToFront()

        val renderer = mapView.artboardRenderer
        val fps = if(renderer?.hasCppObject == true) mapView.artboardRenderer!!.averageFps else -1f
        binding.txtInfo.text = java.lang.String.format(
            Locale.US,
            "Frame rate: %.1f Hz (%.2f ms)",
            fps,
            1e3f / fps
        )
        binding.txtInfo.bringToFront()

//        binding.map.getDimensions{ width, height ->
//            text = "Altura/Height: $height" + "\n" + "Largura/Width: $width"
//
//        }

    }

    private fun setupListeners(){
        binding.btnCloseSdk.onDebouncedListener{
            Toast.makeText(this, "Go out SDK", Toast.LENGTH_SHORT).show()
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnHelp.onDebouncedListener {
            if(binding.menuTop.visibility == View.VISIBLE){
                binding.menuTop.visibility = View.GONE
            }else{
                binding.menuTop.visibility = View.VISIBLE
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

        binding.btnAvatar.onDebouncedListener {
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

        binding.bckIconMap.onDebouncedListener {
            val intent = Intent(this, DestinationActivity::class.java)
            startActivity(intent)
        }

        binding.btn1.setOnClickListener {
            //binding.map.fit = Fit.FIT_HEIGHT
            //binding.map.fit = Fit.FILL
        }

        binding.btn2.setOnClickListener {
            //binding.map.fit = Fit.COVER
        }

        binding.btn3.setOnClickListener {
            //binding.map.fit = Fit.SCALE_DOWN
        }

        riveListeners()

    }

    private fun riveListeners(){
        //Rive.init(context = this)
        //binding.map.animation

        //val artboard = binding.map.artboardName
        //val element = artboard?.get(0)

        //val stateMachineInstance = element.stateMachineInstance

        val listener = object : RiveArtboardRenderer.Listener {
            override fun notifyLoop(animation: PlayableInstance) {
                val a = 10
            }

            override fun notifyPause(animation: PlayableInstance) {
                val b = 10
            }

            override fun notifyPlay(animation: PlayableInstance) {
                val c = 10
            }

            override fun notifyStateChanged(stateMachineName: String, stateName: String) {
                val d = stateMachineName
                val e = stateName
                Toast.makeText(applicationContext, "StatemachineName: $stateMachineName \n StateName: $stateName", Toast.LENGTH_LONG).show()
            }

            override fun notifyStop(animation: PlayableInstance) {
                val f = 10
            }
        }

        mapView.registerListener(listener)

//        binding.map.setOnClickListener {
//            val a = 10
//        }

    }

    private fun setupObservers(){
        viewModel.mapStructure.observe(this) { bytes ->
//            mapView.setRiveBytes(
//                autoplay = true,
//                bytes = bytes,
//                fit = Fit.FIT_HEIGHT,
//                alignment = Alignment.CENTER,
//                loop = Loop.LOOP
//            )
        }

        viewModel.avatarName.observe(this) {
            //binding.topHome.nameAvatar.text = it
        }

        viewModel.loading.observe(this) {
            if (it) {
                binding.loadingBox.visibility = View.VISIBLE
                binding.mapContainer.visibility = View.GONE
                mapView.visibility = View.GONE
            } else {
                binding.loadingBox.visibility = View.GONE
                binding.mapContainer.visibility = View.VISIBLE
                mapView.visibility = View.VISIBLE
            }
        }
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