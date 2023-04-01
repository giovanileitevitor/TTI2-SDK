package com.timwe.tti2sdk.ui.destinations

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.databinding.ActivityDestinationBinding
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class DestinationActivity: AppCompatActivity() {

    private lateinit var binding : ActivityDestinationBinding
    private val viewModel: DestinationViewModel by viewModel()

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
    }

    private fun setupView(){
        binding.loadingBox.visibility = View.VISIBLE
        viewModel.getDetailsFromDestinationId(id = 10)
    }

    private fun setupListeners(){
        binding.btnBackDestinations.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnShareDestination.onDebouncedListener {
            Toast.makeText(this, "Sharing Destination...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers(){
        viewModel.destinationResult.observe(this, Observer{
            showDestination(destination = it)
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
}