package com.timwe.tti2sdk.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.rive.runtime.kotlin.core.Alignment
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Loop
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivityHomeBinding
import com.timwe.tti2sdk.ui.missions.MissionsActivity
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.tti2sdk.ui.team.TeamActivity
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
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupElements()
        setupListeners()
        setupObservers()
    }

    private fun setupElements(){
        viewModel.getdata()
        //viewModel.getMap()
    }

    private fun setupView(){
        binding.map.getDimensions{ width, height ->
            text = "Altura/Height: $height" + "\n" + "Largura/Width: $width"
            binding.txtInfo.text = text
            binding.txtInfo.bringToFront()
        }

        mapView.setRiveResource(
            resId = R.raw.map_main,
            fit = Fit.FIT_HEIGHT,
            autoplay = true,
            alignment = Alignment.CENTER
        )
        //val fps = mapView.renderer.averageFps
        //binding.txtInfo.text = text + "\n" + "frameRate: ${fps.toString()}"

    }

    private fun setupListeners(){
        binding.ttiToolbar.setRightClickListener {
            Toast.makeText(this, "botao help", Toast.LENGTH_SHORT).show()
        }

        binding.ttiToolbar.setLeftClickListener{
            Toast.makeText(this, "Go out SDK", Toast.LENGTH_SHORT).show()
            onBackPressedDispatcher.onBackPressed()
        }

        binding.iconTeam.onDebouncedListener {
            val intent = Intent(this, TeamActivity::class.java)
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

    private fun setupObservers(){
        viewModel.mapStructure.observe(this) { bytes ->
            mapView.setRiveBytes(
                autoplay = true,
                bytes = bytes,
                fit = Fit.FIT_HEIGHT,
                alignment = Alignment.CENTER,
                loop = Loop.LOOP
            )
        }

        viewModel.avatarName.observe(this) {
            //binding.topHome.nameAvatar.text = it
        }

        viewModel.loading.observe(this) {
            if (it) {
                binding.loadingBox.visibility = View.VISIBLE
            } else {
                binding.loadingBox.visibility = View.GONE
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