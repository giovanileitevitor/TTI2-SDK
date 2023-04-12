package com.timwe.tti2sdk.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.RiveArtboardRenderer
import app.rive.runtime.kotlin.core.*
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.ActivityHomeBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.base.riveComponents.RiveButton
import com.timwe.tti2sdk.ui.onboarding.OnBoardingActivity
import com.timwe.tti2sdk.ui.missions.MissionsActivity
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.tti2sdk.ui.board.LeaderBoardActivity
import com.timwe.tti2sdk.ui.destinations.DestinationActivity
import com.timwe.tti2sdk.ui.helpwebview.HelpWebViewActivity
import com.timwe.utils.getDimensions
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.getScopeName
import java.lang.annotation.Native
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeActivity: AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding : ActivityHomeBinding
    private var text: String = ""
    private var usingSystemBackStack = false
    //private val mapView by lazy(LazyThreadSafetyMode.NONE) { binding.map }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Rive.init(applicationContext)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
        setupElements()
    }

    private fun setupElements(){
        viewModel.startLoading()
        viewModel.getData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRive(){
        val stateMachineName = "Game_Progress_Machine"
        val artBoardName = "Game_Board"
//        mapView.setRiveResource(
//            resId = R.raw.map_main_prod_03,
//            autoplay = true,
//            fit = Fit.FILL,
//            alignment = Alignment.CENTER,
//            loop = Loop.LOOP,
//            stateMachineName = stateMachineName
//        )
//
//        val artboards = arrayListOf<String>()
//
//        mapView.file?.artboardNames?.forEach {
//            artboards.add(it)
//        }
//
//        val artboardsFiltered = artboards.filter{ it.startsWith("POI_")}
//
//        val size = artboards.size
//        val sizeFilter = artboardsFiltered.size
//
//       for (i in 0..size){
//           var name = "BTN$i"
//           mapView.fireState(stateMachineName, "ClickPOI")
//           //mapView.setBooleanState( artboards[i], name, true)
//       }
//
//        val listener = object : RiveArtboardRenderer.Listener {
//            override fun notifyLoop(animation: PlayableInstance) {
//                val a = 10
//            }
//
//            override fun notifyPause(animation: PlayableInstance) {
//                val b = 10
//            }
//
//            override fun notifyPlay(animation: PlayableInstance) {
//                val position = (animation as NativeObject).cppPointer
//                //Toast.makeText(applicationContext, "Position: ${position.toString()}", Toast.LENGTH_SHORT).show()
//                Log.i("SDK", "Position: ${position.toString()}")
//            }
//
//            override fun notifyStateChanged(stateMachineName: String, stateName: String) {
//                Log.i("SDK", "StatemachineName: $stateMachineName \n StateName: $stateName")
//                //Toast.makeText(applicationContext, "StatemachineName: $stateMachineName \n StateName: $stateName", Toast.LENGTH_LONG).show()
//            }
//
//            override fun notifyStop(animation: PlayableInstance) {
//                val f = 10
//            }
//
//    }

    //mapView.registerListener(listener)





//        binding.mapContainer.apply {
//            overScrollMode = ScrollView.OVER_SCROLL_NEVER
//            //horizontalScrollbarThumbDrawable = getColor(R.color.parcial_transparent)
//            isHorizontalScrollBarEnabled = false
//        }

//        mapView.artboardRenderer?.file?.firstArtboard?.name.let { artboard ->
//           val stateMachines = artboard.toString()
//        }

//        val size = mapView.stateMachines.size
//        Log.i("SDK", "state machine size: $size")

//        mapView.play(
//            "Game_Progress_Machine",
//            loop = Loop.LOOP,
//            direction = Direction.AUTO,
//            isStateMachine = true
//        )

//        val states = listOf<String>("ClickPOI")

        //mapView.fireState("Game_Progress_Machine", states[0])
//        mapView.fireState("Game_Progress_Machine", states[1])
//        mapView.fireState("Game_Progress_Machine", states[2])


//        mapView.stateMachines.forEach {
//            Log.i("SDK", "state machine: ${it.name}")
//        }

//        binding.map.bringToFront()

//        val renderer = mapView.artboardRenderer
//        val fps = if(renderer?.hasCppObject == true) mapView.artboardRenderer!!.averageFps else -1f
//        binding.txtInfo.text = java.lang.String.format(
//            Locale.US,
//            "Frame rate: %.1f Hz (%.2f ms)",
//            fps,
//            1e3f / fps
//        )
//        binding.txtInfo.bringToFront()

//        binding.map.getDimensions{ width, height ->
//            text = "Altura/Height: $height" + "\n" + "Largura/Width: $width"
//
//        }




//        mapView.setOnClickListener { it ->
//            val x = it.scaleX
//            val y = it.scaleY
//            //Toast.makeText(applicationContext, "PositionX: ${x.toString()} | PositionY: ${y.toString()}", Toast.LENGTH_SHORT).show()
//        }

        //(it as RiveAnimationView).file.artboardNames

//        mapView.setOnCapturedPointerListener { view, motionEvent ->
//            val positionX = view.x
//            val positionY = view.y
//            val horizontalOffset: Float = motionEvent.x
//            val verticalOffset: Float = motionEvent.y
//            Toast.makeText(applicationContext,
//                "PositionX: ${positionX.toString()} | " +
//                        "PositionY: ${positionY.toString()}",
//                            Toast.LENGTH_SHORT).show()
//            true
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
            //mapView.play(Loop.LOOP)
        }

        binding.btn2.setOnClickListener {
            //mapView.play(Loop.AUTO)
        }

        binding.btn3.setOnClickListener {
            //mapView.stop()
        }

        binding.mapView.setOnTouchListener{ _, event->
           when(event.action){
               MotionEvent.ACTION_DOWN ->{
                   val x = event.x
                   val y = event.y

                   binding.txtInfo.text = "PositionX/y: $x/$y"

                   viewModel.findItemClicked(xValue = x, yValue = y)
                   true
               }
               else -> false
           }
        }

    }



    private fun riveListeners(){

//        val states = listOf<String>("ClickPOI")
//        mapView.setNumberState("Game_Progress_Machine", states[0], 1.0f)
//        mapView.setNumberState("Game_Progress_Machine", states[0], 2.0f)
//        mapView.setNumberState("Game_Progress_Machine", states[0], 3.0f)
//        mapView.setNumberState("Game_Progress_Machine", states[0], 4.0f)
//        mapView.setNumberState("Game_Progress_Machine", states[0], 5.0f)
//       // mapView.setBooleanState("Game_Progress_Machine", states[0], true)
//
//        val listener = object : RiveArtboardRenderer.Listener {
//            override fun notifyLoop(animation: PlayableInstance) {
//                val a = 10
//            }
//
//            override fun notifyPause(animation: PlayableInstance) {
//                val b = 10
//            }
//
//            override fun notifyPlay(animation: PlayableInstance) {
//                val position = (animation as NativeObject).cppPointer
//                //Toast.makeText(applicationContext, "Position: ${position.toString()}", Toast.LENGTH_SHORT).show()
//                Log.i("SDK", "Position: ${position.toString()}")
//            }
//
//            override fun notifyStateChanged(stateMachineName: String, stateName: String) {
//                Log.i("SDK", "StatemachineName: $stateMachineName \n StateName: $stateName")
//                Toast.makeText(applicationContext, "StatemachineName: $stateMachineName \n StateName: $stateName", Toast.LENGTH_LONG).show()
//            }
//
//            override fun notifyStop(animation: PlayableInstance) {
//                val f = 10
//            }
//        }
//
//        mapView.registerListener(listener)


//        val stateMachineInstance = mapView.stateMachines
//        stateMachineInstance.forEach {  }

    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                binding.mapContainer.visibility = View.GONE
            } else {
                binding.loadingBox.visibility = View.GONE
                binding.mapContainer.visibility = View.VISIBLE
                binding.mapContainer.visibility = View.VISIBLE
                setupRive()
                riveListeners()
            }
        }

        viewModel.itemClicked.observe(this){ position ->
            Toast.makeText(applicationContext, "Posicao encontrada: $position", Toast.LENGTH_SHORT).show()
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

    data class Position(
        var position: Int,
        var coordenateX: Float,
        var coordenateY: Float
    )

}