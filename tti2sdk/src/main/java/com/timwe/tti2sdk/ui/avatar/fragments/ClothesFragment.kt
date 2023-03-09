package com.timwe.tti2sdk.ui.avatar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timwe.tti2sdk.databinding.FragmentClothesBinding
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment

class ClothesFragment: BaseFragment() {

    private lateinit var binding : FragmentClothesBinding

    companion object ClothesStats {
        fun newInstance(): ClothesFragment {
            return ClothesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClothesBinding.inflate(inflater, container,false);
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