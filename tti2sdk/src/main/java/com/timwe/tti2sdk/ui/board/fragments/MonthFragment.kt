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
import com.timwe.tti2sdk.databinding.FragmentMonthBinding
import com.timwe.tti2sdk.ui.board.LeaderBoardViewModel
import com.timwe.tti2sdk.ui.board.adapter.AllPlacesAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MonthFragment : Fragment() {

    private var _binding : FragmentMonthBinding? = null
    private val binding get() = _binding!!
    private lateinit var monthAdapter: AllPlacesAdapter
    private val viewModel: LeaderBoardViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonthBinding.inflate(inflater, container, false)
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

        viewModel.boardsMonth.observe(this){
            setupMonthAdapter(boards = it)
        }

    }

    private fun setupMonthAdapter(boards: List<Board>){
        binding.rvMonth.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        monthAdapter = AllPlacesAdapter(requireContext(), boards, singleClick )
        binding.rvMonth.adapter = monthAdapter
    }

    private val singleClick = { board: Board ->
        Toast.makeText(requireContext(), "Id: ${board.boardName}", Toast.LENGTH_SHORT).show()
    }

    companion object AvailableStats {
        fun newInstance(): MonthFragment {
            return MonthFragment()
        }
    }

}