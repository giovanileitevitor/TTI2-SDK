package com.timwe.tti2sdk.ui.destinations

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.Prize
import com.timwe.tti2sdk.data.model.response.Wikipedia
import com.timwe.tti2sdk.databinding.ActivityDestinationBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.destinations.adapters.CarrousselAdapter
import com.timwe.tti2sdk.ui.destinations.adapters.PlaceAdapter
import com.timwe.tti2sdk.ui.destinations.adapters.PrizeAdapter
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.tti2sdk.ui.home.HomeActivity
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel


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

        binding.btnShareDestination.onDebouncedListener {
            Toast.makeText(this, "Sharing Destination...", Toast.LENGTH_SHORT).show()
        }

        binding.txtAllPrizes.onDebouncedListener {
            Toast.makeText(this, "Under development...", Toast.LENGTH_SHORT).show()
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
            showDestination(destinationInfo = it)
            showPrizes(prizes = it.prizes)
            showPlaces(places = it.placesAll)
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
        binding.carroussel.visibility = View.VISIBLE
        binding.txtProfileLabel.text = destinationInfo.title
        binding.carroussel.adapter = CarrousselAdapter(
            data = destinationInfo.images ?: emptyList(),
            mGlide = Glide.with(this),
            itemListener = singleImageClick
        )

        TabLayoutMediator(binding.dotsCarroussel, binding.carroussel){ tab, position ->
        }.attach()
        binding.txtCityNumber.text = destinationInfo.order.toString()
        binding.txtCityCode.text = destinationInfo.cityCode
        binding.txtTitleDescription.text = destinationInfo.title
        binding.txtDetailsDescription.text = destinationInfo.description
        binding.txtLinkFindMore.onDebouncedListener {
            if(!destinationInfo.urlLink.isNullOrEmpty()){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(destinationInfo.urlLink))
                startActivity(intent)
            } else{
                Toast.makeText(this, getString(R.string.invalid_url), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val singleImageClick = { string: String ->

    }

    private fun showPrizes(prizes: List<Prize>){
        binding.rvPrizes.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPrizes.layoutManager = layoutManager
        binding.rvPrizes.adapter = PrizeAdapter(
            context = this,
            data = prizes,
            mGlide = Glide.with(this),
            itemListener = singlePrizeClick
        )
    }

    private val singlePrizeClick = { prize: Prize ->
        Toast.makeText(this, "Prize code: ${prize.id}", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(this, "Place code: ${wikipedia.id}", Toast.LENGTH_SHORT).show()
    }


}