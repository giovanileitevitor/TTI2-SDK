package com.timwe.tti2sdk.ui.destinations

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
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

//        binding.radioGroupOptions.setOnCheckedChangeListener { group, checkedId ->
//            when(checkedId){
//                R.id.rdAll -> {
//                    Toast.makeText(this, "All selected", Toast.LENGTH_SHORT).show()
//                    showPlaces(listWikipedia = viewModel.getDetination().placesAll)
//                }
//                R.id.rdFood -> {
//                    Toast.makeText(this, "Food selected", Toast.LENGTH_SHORT).show()
//                    showPlaces(listWikipedia = viewModel.getDetination().placesFood)
//                }
//                R.id.rdSights -> {
//                    Toast.makeText(this, "Sights selected", Toast.LENGTH_SHORT).show()
//                    showPlaces(listWikipedia = viewModel.getDetination().placesSights)
//                }
//                R.id.rdStays -> {
//                    Toast.makeText(this, "Stays selected", Toast.LENGTH_SHORT).show()
//                    showPlaces(listWikipedia = viewModel.getDetination().placesStays)
//                }
//            }
//        }
    }

    private fun setupObservers(){
        viewModel.cityId.observe(this, Observer{ cityId ->
            viewModel.getDetailsFromDestinationId(cityId = cityId)
            binding.txtId.text = "Id/CityNumber: $cityId / $cityIdAux "
        })

        viewModel.destinationResult.observe(this, Observer{
            showDestination(destinationInfo = it)
            showPrizes(prizes = it.prizes)
            showPlaces()
        })

        viewModel.loading.observe(this, Observer {
            if(it){
                binding.loadingBox.visibility = View.VISIBLE
            }else{
                binding.loadingBox.visibility = View.GONE
            }
        })

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
        binding.carroussel.adapter = CarrousselAdapter(
            data = destinationInfo.images ?: emptyList(),
            mGlide = Glide.with(this),
            itemListener = singleImageClick
        )
        binding.dotsCarroussel.attachTo(binding.carroussel)
        binding.txtCityNumber.text = destinationInfo.order.toString()
        binding.txtCityCode.text = destinationInfo.cityCode
        binding.txtTitleDescription.text = destinationInfo.title
        binding.txtDetailsDescription.text = destinationInfo.description
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

    }

    private fun showPlaces(){

    }


}