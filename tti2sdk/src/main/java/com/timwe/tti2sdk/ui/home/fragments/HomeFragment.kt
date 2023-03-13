package com.timwe.tti2sdk.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.timwe.tti2sdk.databinding.FragmentHomeBinding
import com.timwe.tti2sdk.ui.FragmentId
import com.timwe.tti2sdk.ui.Navigation
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import com.timwe.tti2sdk.ui.home.HomeActivity
import com.timwe.tti2sdk.ui.home.HomeViewModel
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding : FragmentHomeBinding

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
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.iconMissions.setOnClickListener {
            Log.i("MISSIONS", "click")
            val fragment: BaseFragment = Navigation.getFragmentFromFragmentId(FragmentId.MISSIONS)
            ((activity) as HomeActivity).showNewFragmentAndAddToSystemBackStack(fragment)
        }

        binding.iconPrizes.setOnClickListener {
            Log.i("PRIZES", "click")
        }

        binding.iconTeam.setOnClickListener{
            Log.i("TEAM", "click")
        }

        binding.topHome.ivAvatar.setOnClickListener {
            val intent = Intent(requireActivity(), AvatarActivity::class.java)
            startActivity(intent)
        }

        binding.topHome.nameAvatar.text = "Felippe dos Santos Ferreira"

    }

    override fun onResume() {
        super.onResume()
        setupObservers()
        setupListeners()
    }

    private fun setupObservers(){

    }

    private fun setupListeners(){
        binding.iconTeam.onDebouncedListener {
            Toast.makeText(requireContext(), "Botao icone", Toast.LENGTH_SHORT).show()
        }

        binding.iconMissions.onDebouncedListener {
            Toast.makeText(requireContext(), "Botao Missions", Toast.LENGTH_SHORT).show()
        }

        binding.iconPrizes.onDebouncedListener {
            Toast.makeText(requireContext(), "Botao Prizes", Toast.LENGTH_SHORT).show()
        }
    }

}