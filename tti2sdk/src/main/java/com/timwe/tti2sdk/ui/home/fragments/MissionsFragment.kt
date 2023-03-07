package com.timwe.tti2sdk.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timwe.tti2sdk.databinding.FragmentMissionsBinding
import com.timwe.tti2sdk.ui.fragments.BaseFragment

class MissionsFragment: BaseFragment() {

    private var binding : FragmentMissionsBinding? = null

    companion object MissionsStats {
        fun newInstance(): MissionsFragment {
            return MissionsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMissionsBinding.inflate(inflater, container,false);
        val view = binding?.root;
        return view;
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}