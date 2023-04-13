package com.timwe.tti2sdk.ui.destinations

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.Place
import com.timwe.tti2sdk.data.entity.Prize
import com.timwe.tti2sdk.databinding.ActivityDestinationBinding
import com.timwe.tti2sdk.ui.destinations.adapters.PlaceAdapter
import com.timwe.tti2sdk.ui.destinations.adapters.PrizeAdapter
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class DestinationActivity: AppCompatActivity() {

    private lateinit var binding : ActivityDestinationBinding
    private val viewModel: DestinationViewModel by viewModel()
    private lateinit var prizeAdapter: PrizeAdapter
    private lateinit var placeAdapter: PlaceAdapter

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
        onBackPressedDispatcher.onBackPressed()
        finish()
    }

    private fun setupView(){
        val destinationId = intent.getStringExtra("DESTINATION_ID") ?: ""
        binding.txtId.text = getString(R.string.city_id, destinationId)
        viewModel.getDetailsFromDestinationId(id = 10, buttonId = destinationId)
    }

    private fun setupListeners(){
        binding.btnBackDestinations.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.btnShareDestination.onDebouncedListener {
            Toast.makeText(this, "Sharing Destination...", Toast.LENGTH_SHORT).show()
        }

        binding.radioGroupOptions.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rdAll -> {
                    Toast.makeText(this, "All selected", Toast.LENGTH_SHORT).show()
                }
                R.id.rdFood -> {
                    Toast.makeText(this, "Food selected", Toast.LENGTH_SHORT).show()
                }
                R.id.rdSights -> {
                    Toast.makeText(this, "Sights selected", Toast.LENGTH_SHORT).show()
                }
                R.id.rdStays -> {
                    Toast.makeText(this, "Stays selected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupObservers(){
        viewModel.destinationResult.observe(this, Observer{
            showDestination(destination = it)
        })

        viewModel.prizes.observe(this, Observer {
            showPrizes(prizes = it)
        })

        viewModel.places.observe(this, Observer {
            showPlaces(places = it)
        })

        viewModel.loading.observe(this, Observer {
            if(it){
                binding.loadingBox.visibility = View.VISIBLE
            }else{
                binding.loadingBox.visibility = View.GONE
            }
        })
    }

    private fun showDestination(destination: Destination) {
        destination.let {
            binding.txtTitleDescription.text = it.title
            binding.txtSubtitleDescription.text = it.subtitle
            binding.txtDetailsDescription.text = it.description
            binding.txtLinkDestination.text = it.urlLink
        }
    }

    private fun showPrizes(prizes: List<Prize>){
        binding.rvPrizes.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        prizeAdapter = PrizeAdapter(prizes, Glide.with(this), singlePrizeClick)
        binding.rvPrizes.adapter = prizeAdapter
    }

    private val singlePrizeClick = { prize: Prize ->
        Toast.makeText(this, "Id Prize: ${prize.id}", Toast.LENGTH_SHORT).show()
    }


    private fun showPlaces(places: List<Place>){
        binding.rvAroundHere.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        placeAdapter = PlaceAdapter(places, Glide.with(this), singlePlaceClick)
        binding.rvAroundHere.adapter = placeAdapter
    }

    private val singlePlaceClick = { place: Place ->
        Toast.makeText(this, "Id Place: ${place.id}", Toast.LENGTH_SHORT).show()
    }
}