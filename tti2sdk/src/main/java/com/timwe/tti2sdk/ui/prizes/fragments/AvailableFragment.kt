package com.timwe.tti2sdk.ui.prizes.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.model.response.AvailableReward
import com.timwe.tti2sdk.databinding.FragmentAvailableBinding
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.tti2sdk.ui.prizes.fragments.adapter.AdapterPrizes
import com.timwe.tti2sdk.ui.prizes.fragments.viewmodel.TabsPrizesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AvailableFragment: BaseFragment() {

    private lateinit var binding : FragmentAvailableBinding
    private val viewModel: TabsPrizesViewModel by sharedViewModel()
    private var sizeBadge = 0

    companion object{

        const val PRIZES = "PRIZES"
        const val BOARDS = "BOARDS"

        fun newInstance(): AvailableFragment {
            return AvailableFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAvailableBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var prize: PrizeFlow? = null
        val bundle = this.arguments
        if (bundle != null) {
            prize = bundle.getSerializable(PRIZES) as PrizeFlow
            viewModel.savePrize(prizeFlow = prize)
        }

        var adapterPrizes: AdapterPrizes? = null
        viewModel.prizeAvailableLiveData.observe(viewLifecycleOwner, Observer {

            sizeBadge = ((activity) as PrizesActivity).getSizeBadge(it)
            ((activity) as PrizesActivity).setSizeTab(tabSelected = 0, size = sizeBadge)

            if(adapterPrizes == null){
                adapterPrizes = AdapterPrizes(
                    context = requireContext(),
                    resource = R.layout.item_list_prizes_available,
                    prize = it,
                    mGlide = Glide.with(this),
                    typeViewHolder = AdapterPrizes.PRIZES_AVAILABLE,
                ){ availableReward ->
                    ((activity) as PrizesActivity).showDialog(availableReward = availableReward)
                }
                binding.recyclerViewVoucher.adapter = adapterPrizes
            }
        })
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        ((activity) as PrizesActivity).setSizeTab(tabSelected = 0, size = sizeBadge)
    }

}