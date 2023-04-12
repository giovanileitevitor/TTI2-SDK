package com.timwe.tti2sdk.ui.prizes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.databinding.FragmentHistoryBinding
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import com.timwe.tti2sdk.ui.prizes.fragments.adapter.AdapterPrizes
import com.timwe.tti2sdk.ui.prizes.fragments.viewmodel.TabsPrizesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HistoryFragment: BaseFragment() {

    private lateinit var binding : FragmentHistoryBinding
    private val viewModel: TabsPrizesViewModel by sharedViewModel()

    companion object AvailableStats {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var adapterPrizes: AdapterPrizes? = null
        viewModel.prizeHistoryLiveData.observe(viewLifecycleOwner, Observer {
            if(adapterPrizes == null){
                adapterPrizes = AdapterPrizes(
                    context = requireContext(),
                    prize = it,
                    mGlide = Glide.with(this),
                    typeViewHolder = AdapterPrizes.PRIZES_HISTORY,
                ){ _ -> }
                binding.recyclerPrizesHistory.adapter = adapterPrizes
            }
        })

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if(menuVisible){
            viewModel.setTabHistory()
        }
    }

}