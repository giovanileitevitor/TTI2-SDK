package com.timwe.tti2sdk.ui.avatar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timwe.tti2sdk.databinding.FragmentShoesBinding
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment

class ShoesFragment: BaseFragment() {

    private lateinit var binding : FragmentShoesBinding

    companion object ShoesStats {
        fun newInstance(): ShoesFragment {
            return ShoesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoesBinding.inflate(inflater, container,false)
        val view = binding.root
        return view
    }

}