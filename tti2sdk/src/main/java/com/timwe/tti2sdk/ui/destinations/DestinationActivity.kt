package com.timwe.tti2sdk.ui.destinations

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
import com.timwe.tti2sdk.data.model.response.Wikipedia
import com.timwe.tti2sdk.databinding.ActivityDestinationBinding
import com.timwe.tti2sdk.ui.destinations.adapters.PlaceAdapter
import com.timwe.tti2sdk.ui.dialog.DialogError
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class DestinationActivity: AppCompatActivity() {

    private lateinit var binding : ActivityDestinationBinding
    private val viewModel: DestinationViewModel by viewModel()
    private var placeAdapter: PlaceAdapter? =  null

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
        finish()
    }

    private fun setupView(){
        val destinationId = intent.getStringExtra("DESTINATION_ID") ?: ""
        binding.txtId.text = getString(R.string.city_id, destinationId)
        viewModel.getDetailsFromDestinationId()
    }

    private fun setupListeners(){
        binding.btnBackDestinations.setOnClickListener {
            finish()
        }

        binding.btnShareDestination.onDebouncedListener {
            Toast.makeText(this, "Sharing Destination...", Toast.LENGTH_SHORT).show()
        }

        binding.radioGroupOptions.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rdAll -> {
                    Toast.makeText(this, "All selected", Toast.LENGTH_SHORT).show()
                    showPlaces(listWikipedia = viewModel.getDetination().placesAll)
                }
                R.id.rdFood -> {
                    Toast.makeText(this, "Food selected", Toast.LENGTH_SHORT).show()
                    showPlaces(listWikipedia = viewModel.getDetination().placesFood)
                }
                R.id.rdSights -> {
                    Toast.makeText(this, "Sights selected", Toast.LENGTH_SHORT).show()
                    showPlaces(listWikipedia = viewModel.getDetination().placesSights)
                }
                R.id.rdStays -> {
                    Toast.makeText(this, "Stays selected", Toast.LENGTH_SHORT).show()
                    showPlaces(listWikipedia = viewModel.getDetination().placesStays)
                }
            }
        }
    }

    private fun setupObservers(){
        viewModel.destinationResult.observe(this, Observer{
            showDestination(destination = it)
            showPlaces(listWikipedia = it.placesAll)
        })

        viewModel.loading.observe(this, Observer {
            if(it){
                binding.linearTotal.visibility = View.VISIBLE
                binding.loadingBox.visibility = View.VISIBLE
            }else{
                binding.linearTotal.visibility = View.GONE
                binding.loadingBox.visibility = View.GONE
            }
        })

        viewModel.error.observe(this, Observer { it ->
            DialogError(
                this@DestinationActivity,
                it.errorCode!!,
                object : DialogError.ClickListenerDialogError{
                    override fun reloadClickListener() {
                        viewModel.getDetailsFromDestinationId()
                    }
                }
            )
        })

    }

    private fun showDestination(destination: Destination) {
        destination.let {

            Glide.with(this)
                .load(it.imageTop)
                .priority(Priority.HIGH)
                .into(binding.imgMainDestination)

            binding.txtTitleDescription.text = it.title
            binding.txtSubtitleDescription.text = it.title
            binding.txtDetailsDescription.text = it.description
            binding.txtLinkDestination.text = it.urlLink
        }
    }

    private fun showPlaces(listWikipedia: List<Wikipedia>){
        if(placeAdapter == null){
            binding.rvAroundHere.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            placeAdapter = PlaceAdapter(listWikipedia, Glide.with(this), singlePlaceClick)
            binding.rvAroundHere.adapter = placeAdapter
        }else{
            placeAdapter?.replaceItens(newData = listWikipedia)
        }
    }

    private val singlePlaceClick = { place: Wikipedia ->
        Toast.makeText(this, "Id Place: ${place.id}", Toast.LENGTH_SHORT).show()
    }

}