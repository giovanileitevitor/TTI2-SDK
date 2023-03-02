package com.timwe.tti2sdk.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timwe.tti2sdk.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment() {

    private var binding : FragmentHomeBinding ? = null

    companion object HomeStats {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container,false);
        val view = binding?.root;
        return view;
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}