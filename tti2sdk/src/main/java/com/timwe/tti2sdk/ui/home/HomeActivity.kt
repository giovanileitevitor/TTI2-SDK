package com.timwe.tti2sdk.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.OverScroller
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.rive.runtime.kotlin.RiveArtboardRenderer
import app.rive.runtime.kotlin.core.Alignment
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Loop
import app.rive.runtime.kotlin.core.PlayableInstance
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivityHomeBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.help.HelpActivity
import com.timwe.tti2sdk.ui.missions.MissionsActivity
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.tti2sdk.ui.board.LeaderBoardActivity
import com.timwe.tti2sdk.ui.destinations.DestinationActivity
import com.timwe.utils.getDimensions
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            resId = R.raw.map_main_prod_01_02_new,
            autoplay = true,
            fit = Fit.SCALE_DOWN,
            alignment = Alignment.CENTER,
            loop = Loop.LOOP
        )

        mapView.bringToFront()

        binding.containerMapConstraint.getDimensions{ width, height ->
            text = "Altura/Height: $height" + "\n" + "Largura/Width: $width"
            binding.txtInfo.text = text
            binding.txtInfo.bringToFront()
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
            }else{
                binding.menuTop.visibility = View.VISIBLE
            }
        }

        binding.itemMenuGameHelp.onDebouncedListener {
            binding.menuTop.visibility = View.GONE
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }

        binding.itemMenuReplay.onDebouncedListener {
            binding.menuTop.visibility = View.GONE
            Toast.makeText(this, "Replay Button", Toast.LENGTH_SHORT).show()
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



        val listener = object : RiveArtboardRenderer.Listener {
            override fun notifyLoop(animation: PlayableInstance) {
                TODO("Not yet implemented")
            }

            override fun notifyPause(animation: PlayableInstance) {
                TODO("Not yet implemented")
            }

            override fun notifyPlay(animation: PlayableInstance) {
                TODO("Not yet implemented")
            }

            override fun notifyStateChanged(stateMachineName: String, stateName: String) {
                val a = stateMachineName
                val b = stateName
            }

            override fun notifyStop(animation: PlayableInstance) {
                TODO("Not yet implemented")
            }
        }


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