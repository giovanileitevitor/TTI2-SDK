package com.timwe.tti2sdk.ui.board.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timwe.tti2sdk.databinding.FragmentAlltimeBinding
import com.timwe.tti2sdk.ui.board.adapter.AllPlacesAdapter

class AllTimeFragment : Fragment() {

    private var _binding : FragmentAlltimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var allPlacesAdapter: AllPlacesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlltimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupListeners()
    }

    private fun setupView(){

    }

    private fun setupListeners(){

    }

    companion object AvailableStats {
        fun newInstance(): AllTimeFragment {
            return AllTimeFragment()
        }
    }

}