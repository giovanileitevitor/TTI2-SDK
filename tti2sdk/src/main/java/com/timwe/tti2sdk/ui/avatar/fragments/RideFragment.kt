package com.timwe.tti2sdk.ui.avatar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timwe.tti2sdk.databinding.FragmentRideBinding
import com.timwe.tti2sdk.ui.fragments.BaseFragment

class RideFragment: BaseFragment() {

    private lateinit var binding : FragmentRideBinding

    companion object RideStats {
        fun newInstance(): RideFragment {
            return RideFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRideBinding.inflate(inflater, container,false);
        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}