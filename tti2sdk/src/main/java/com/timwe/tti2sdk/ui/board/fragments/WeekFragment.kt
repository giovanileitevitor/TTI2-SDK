package com.timwe.tti2sdk.ui.board.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timwe.tti2sdk.databinding.FragmentWeekBinding

class WeekFragment : Fragment(){

    private var _binding : FragmentWeekBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
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
        fun newInstance(): WeekFragment {
            return WeekFragment()
        }
    }

}