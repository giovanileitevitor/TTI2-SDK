package com.timwe.tti2sdk.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.databinding.FragmentHomeBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import com.timwe.tti2sdk.ui.home.HomeViewModel
import com.timwe.tti2sdk.ui.missions.MissionsActivity
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.tti2sdk.ui.team.TeamActivity
import com.timwe.utils.getDimensions
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding : FragmentHomeBinding
    private val mapView by lazy(LazyThreadSafetyMode.NONE) { binding.map }

    companion object HomeStats {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMap()
        setupObservers()
        setupListeners()
    }

    private fun setupView(){
        //binding.topHome.nameAvatar.text = "Felippe dos Santos Ferreira"
        binding.mapContainer.getDimensions{ width, height ->
            binding.txtInfo.text = "Height: $height" + "\n" + "Width: $width"
        }

    }

    private fun setupObservers(){
        viewModel.mapStructure.observe(this, Observer{ bytes ->
            //binding.map.setRiveBytes(bytes = bytes, fit = Fit.FILL)
            binding.map.setRiveResource(R.raw.map_main)
        })

        viewModel.loading.observe(this, Observer{

        })
    }

    private fun setupListeners(){
        binding.topHome.ivAvatar.onDebouncedListener {
            val intent = Intent(activity, AvatarActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.iconTeam.onDebouncedListener {
            val intent = Intent(activity, TeamActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.iconMissions.onDebouncedListener {
            val intent = Intent(activity, MissionsActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.iconPrizes.onDebouncedListener {
            val intent = Intent(activity, PrizesActivity::class.java)
            activity?.startActivity(intent)
        }
    }

}