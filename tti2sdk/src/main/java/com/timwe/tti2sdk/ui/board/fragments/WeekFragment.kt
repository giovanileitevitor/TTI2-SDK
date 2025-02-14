package com.timwe.tti2sdk.ui.board.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timwe.tti2sdk.data.entity.Board
import com.timwe.tti2sdk.databinding.FragmentWeekBinding
import com.timwe.tti2sdk.ui.board.LeaderBoardViewModel
import com.timwe.tti2sdk.ui.board.adapter.AllPlacesAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WeekFragment : Fragment(){

    private var _binding : FragmentWeekBinding? = null
    private val binding get() = _binding!!
    private lateinit var weekAdapter: AllPlacesAdapter
    private val viewModel: LeaderBoardViewModel by sharedViewModel()

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
        setupObservers()
    }

    private fun setupView(){

    }

    private fun setupListeners(){

    }

    private fun setupObservers(){
        viewModel.yourPlace.observe(this){
            binding.txtPosition.text = it.position.toString()
            binding.txtNameLeader.text = it.yourName
            binding.txtIdLeader.text = it.yourId
            binding.txtDistanceLeader.text = it.yourDistance.toString()
            binding.txtKmLeader.text = it.distanceUnit
        }

        viewModel.boardsWeek.observe(this){
            setupWeekAdapter(boards = it)
        }

    }

    private fun setupWeekAdapter(boards: List<Board>){
        binding.rvWeek.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        weekAdapter = AllPlacesAdapter(requireContext(), boards, singleClick )
        binding.rvWeek.adapter = weekAdapter
    }

    private val singleClick = { board: Board ->
        Toast.makeText(requireContext(), "Id: ${board.boardName}", Toast.LENGTH_SHORT).show()
    }

    companion object AvailableStats {
        fun newInstance(): WeekFragment {
            return WeekFragment()
        }
    }

}