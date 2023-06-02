package com.timwe.tti2sdk.ui.destinations

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.Prize
import com.timwe.tti2sdk.data.model.response.Wikipedia
import com.timwe.tti2sdk.databinding.ActivityDestinationBinding
import com.timwe.tti2sdk.ui.destinations.adapters.CarrousselAdapter
import com.timwe.tti2sdk.ui.destinations.adapters.PlaceAdapter
import com.timwe.tti2sdk.ui.destinations.adapters.PrizeAdapter
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.home.HomeActivity
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.utils.Utils
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception
import java.net.URL


class DestinationActivity: AppCompatActivity() {

    private val viewModel: DestinationViewModel by viewModel()
    private lateinit var binding : ActivityDestinationBinding
    private lateinit var prizeAdapter: PrizeAdapter
    private lateinit var placeAdapter: PlaceAdapter
    private var cityIdAux: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
        setupObservers()
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupView(){
        val cityNumber = intent.getLongExtra("DESTINATION_ID", 0)
        cityNumber.let {
            cityIdAux = it
        }
        viewModel.getCityIdfromCityNumber(cityNumber = cityNumber)
    }

    private fun setupListeners(){

        binding.btnBackDestinations.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.txtAllPrizes.onDebouncedListener {
            val intent = Intent(this, PrizesActivity::class.java)
            startActivity(intent)
        }

        setAllTabs()
        binding.tabArroundHere.addOnTabSelectedListener(object: OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) { }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                setTabUnSelected(tab!!.position)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                setTabSelected(tab!!.position)
                when(tab.position){
                0 -> {
                    showPlaces(places = viewModel.getDestination().placesAll)
                }
                1 -> {
                    showPlaces(places = viewModel.getDestination().placesFood)
                }
                2 -> {
                    showPlaces(places = viewModel.getDestination().placesSights)
                }
                3 -> {
                    showPlaces(places = viewModel.getDestination().placesStays)
                }
            }

            }
        })

    }

    private fun setAllTabs() = with(binding){
        tabArroundHere.addTab(tabArroundHere.newTab().setCustomView(R.layout.item_tab_all_selected), 0, true)
        tabArroundHere.addTab(tabArroundHere.newTab().setCustomView(R.layout.item_tab_food_unselected), 1, false)
        tabArroundHere.addTab(tabArroundHere.newTab().setCustomView(R.layout.item_tab_sights_unselected), 2, false)
        tabArroundHere.addTab(tabArroundHere.newTab().setCustomView(R.layout.item_tab_stays_unselected), 3, false)
        tabArroundHere.tabGravity = TabLayout.GRAVITY_FILL
    }

    fun setTabSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.item_tab_all_selected
            1 -> R.layout.item_tab_food_selected
            2 -> R.layout.item_tab_sights_selected
            3 -> R.layout.item_tab_stays_selected
            else -> 0
        }
        binding.tabArroundHere.getTabAt(position)?.setCustomView(layout)
    }

    fun setTabUnSelected(position: Int){
        val layout = when(position){
            0 -> R.layout.item_tab_all_unselected
            1 -> R.layout.item_tab_food_unselected
            2 -> R.layout.item_tab_sights_unselected
            3 -> R.layout.item_tab_stays_unselected
            else -> 0
        }
        binding.tabArroundHere.getTabAt(position)?.setCustomView(layout)
    }

    private fun setupObservers(){
        viewModel.cityId.observe(this) { cityId ->
            viewModel.getDetailsFromDestinationId(cityId = cityId)
            binding.txtId.text = "Id/CityNumber: $cityId / $cityIdAux "
        }

        viewModel.destinationResult.observe(this) {
            if(it.isCapital){

                binding.btnBackDestinations.background = applicationContext.getDrawable(R.drawable.background_arrounded_white)
                binding.btnBackDestinations.setImageResource(R.drawable.ic_back_arrow)

                binding.containerToolbarDestinations.setBackgroundColor(applicationContext.getColor(R.color.red_deastination))
                binding.txtProfileLabel.setTextColor(ContextCompat.getColor(applicationContext, R.color.all_white))
                binding.txtProfileLabel.text = it.title

                binding.btnShareDestination.setImageResource(R.drawable.ico_share_white)
                clickShare(it)

                setCarrousselImage(it)

                binding.containerDetails.background = applicationContext.getDrawable(R.drawable.background_card_destination_dark_blue)

                binding.txtCityNumber.text = it.order.toString()

                binding.txtCityCode.visibility = View.INVISIBLE

                binding.txtTitleDescription.text = it.title
                binding.txtTitleDescription.setTextColor(ContextCompat.getColor(applicationContext, R.color.all_white))

                binding.txtSubtitle.text = getString(R.string.about)
                binding.txtSubtitle.setTextColor(ContextCompat.getColor(applicationContext, R.color.all_white))

                binding.txtDetailsDescription.text = it.about
                binding.txtDetailsDescription.setTextColor(ContextCompat.getColor(applicationContext, R.color.all_white))

                binding.txtSubtitleTwo.text = getString(R.string.background)
                binding.txtSubtitleTwo.setTextColor(ContextCompat.getColor(applicationContext, R.color.all_white))

                binding.txtDetailsDescriptionTwo.text = it.background
                binding.txtDetailsDescriptionTwo.setTextColor(ContextCompat.getColor(applicationContext, R.color.all_white))

                binding.containerWiki.visibility = View.GONE
                binding.markerLeftWiki.visibility = View.GONE
                binding.imageViewContainerWikiMarker.visibility = View.GONE
                binding.nameCardDestination.visibility = View.GONE
                binding.nameCardDestination2.visibility = View.GONE
                binding.imgGo.visibility = View.GONE
                binding.viewDivider.visibility = View.GONE

                binding.viewBorder.visibility = View.GONE

                binding.viewDivider2.visibility = View.GONE

                binding.containerPrizes.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.blue_dark))

                binding.txtPrizesLabel.setTextColor(ContextCompat.getColor(applicationContext, R.color.all_white))

                binding.txtAllPrizes.setTextColor(ContextCompat.getColor(applicationContext, R.color.all_white))

                clickGoPrizes(it)

                binding.containerPrizes.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.blue_dark))

                showPrizes(prizes = it.prizes, darkItens = true)

                binding.containerDestination.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.blue_dark))

                binding.txtAroundHere.visibility = View.GONE

                binding.tabArroundHere.visibility = View.GONE

                binding.rvAroundHere.visibility = View.GONE



            }else{
                showDestination(destinationInfo = it)
                showPrizes(prizes = it.prizes)
                showPlaces(places = it.placesAll)
            }
        }

        viewModel.loading.observe(this) {
            if (it) {
                binding.loadingBox.visibility = View.VISIBLE
                binding.linearTotal.visibility = View.VISIBLE
            } else {
                binding.loadingBox.visibility = View.GONE
                binding.linearTotal.visibility = View.GONE
            }
        }

        viewModel.error.observe(this, Observer { it ->
            DialogError(
                this@DestinationActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError{
                    override fun reloadClickListener() {
                        viewModel.getDetailsFromDestinationId(cityId = cityIdAux)
                    }
                }
            )
        })
    }

    private fun showDestination(destinationInfo : Destination) {
        binding.txtProfileLabel.text = destinationInfo.title

        setCarrousselImage(destinationInfo)

        binding.txtCityNumber.text = destinationInfo.order.toString()
        binding.txtCityCode.text = destinationInfo.cityCode
        binding.txtTitleDescription.text = destinationInfo.title
        binding.txtSubtitleTwo.visibility = View.GONE
        binding.txtDetailsDescriptionTwo.visibility = View.GONE
        binding.txtDetailsDescription.text = destinationInfo.description
        binding.nameCardDestination2.text = destinationInfo.title

        clickGoPrizes(destinationInfo)

        clickShare(destinationInfo)
    }

    private fun clickGoPrizes(destinationInfo: Destination) {
        binding.imgGo.setOnClickListener {
            if (!destinationInfo.urlLink.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(destinationInfo.urlLink))
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.invalid_url), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setCarrousselImage(destinationInfo: Destination) {

        if(destinationInfo.isCapital){
            binding.imageViewNext.visibility = View.VISIBLE
            binding.imageViewPrevious.visibility = View.INVISIBLE

            binding.carroussel.isUserInputEnabled = false
            binding.imageViewNext.onDebouncedListener{
                val position = binding.carroussel.currentItem
                Log.i("position 1", position.toString())
                Log.i("position 2", destinationInfo.images?.size.toString())
                if(destinationInfo.images?.size == (position+2)){
                    binding.imageViewNext.visibility = View.GONE
                    binding.carroussel.setCurrentItem((position + 1), true)
                }else{
                    binding.carroussel.setCurrentItem((position + 1), true)
                }
                binding.imageViewPrevious.visibility = View.VISIBLE
            }

            binding.imageViewPrevious.onDebouncedListener{
                val position = binding.carroussel.currentItem
                Log.i("position 1", position.toString())
                Log.i("position 2", destinationInfo.images?.size.toString())
                if((position == 1)){
                    binding.imageViewPrevious.visibility = View.GONE
                    binding.carroussel.setCurrentItem((position - 1), true)
                }else{
                    binding.carroussel.setCurrentItem((position - 1), true)
                }
                binding.imageViewNext.visibility = View.VISIBLE
            }

        }else{
            binding.imageViewNext.visibility = View.GONE
            binding.imageViewPrevious.visibility = View.GONE
        }

        binding.carroussel.visibility = View.VISIBLE
        binding.carroussel.adapter = CarrousselAdapter(
            data = destinationInfo.images ?: emptyList(),
            mGlide = Glide.with(this),
            itemListener = singleImageClick
        )
        if (destinationInfo.images.isNullOrEmpty() || destinationInfo.images.size == 1) {
            binding.dotsCarroussel.visibility = View.INVISIBLE
        }
        TabLayoutMediator(binding.dotsCarroussel, binding.carroussel) { tab, position ->
        }.attach()
    }

    private fun clickShare(destinationInfo: Destination) {
        binding.btnShareDestination.onDebouncedListener {

            Thread {
                try {

                    val message: String = getString(R.string.txtShare, destinationInfo.title)
                    val imageurl = URL(destinationInfo.shareImageUrl)
                    val bitmap =
                        BitmapFactory.decodeStream(imageurl.openConnection().getInputStream())

                    val uri: Uri? = if (Utils.isExternalStorageWritable()) {
                        Utils.saveImageExternal(bitmap!!, this)
                    } else {
                        Utils.saveImage(bitmap!!, this)
                    }

                    val intent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        this.putExtra(Intent.EXTRA_TEXT, message)
                        this.putExtra(Intent.EXTRA_STREAM, uri)
                        this.type = "image/png"
                    }
                    startActivity(Intent.createChooser(intent, resources.getText(R.string.share)))

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()

        }
    }

    private val singleImageClick = { string: String ->

    }

    private fun showPrizes(prizes: List<Prize>, darkItens: Boolean = false){
        binding.rvPrizes.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvPrizes.layoutManager = layoutManager
        binding.rvPrizes.adapter = PrizeAdapter(
            context = this,
            data = prizes,
            mGlide = Glide.with(this),
            itemListener = singlePrizeClick,
            darkItens = darkItens
        )
    }

    private val singlePrizeClick = { prize: Prize ->
        //Toast.makeText(this, "Prize code: ${prize.id}", Toast.LENGTH_SHORT).show()
    }

    var placeAdapterAroundHere: PlaceAdapter? = null
    private fun showPlaces(places: List<Wikipedia>){
        if(placeAdapterAroundHere == null){
            binding.rvAroundHere.visibility = View.VISIBLE
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvAroundHere.layoutManager = layoutManager
            placeAdapterAroundHere = PlaceAdapter(
                context = this,
                data = places,
                mGlide = Glide.with(this),
                itemListener = singlePlaceClick
            )
            binding.rvAroundHere.adapter = placeAdapterAroundHere

        }else{
            placeAdapterAroundHere?.replaceItens(places)
        }
    }

    private val singlePlaceClick = { wikipedia: Wikipedia ->
        val gmmIntentUri = Uri.parse("${wikipedia.redirectUrl}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}