package com.timwe.tti2sdk.ui.prizes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.Prize
import com.timwe.tti2sdk.databinding.FragmentAvailableBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment
import com.timwe.tti2sdk.ui.avatar.fragments.adapters.AdapterGeneric
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import com.timwe.tti2sdk.ui.prizes.PrizesActivity
import com.timwe.tti2sdk.ui.prizes.PrizesViewModel
import com.timwe.tti2sdk.ui.prizes.fragments.adapter.AdapterPrizes
import com.timwe.tti2sdk.ui.prizes.fragments.viewmodel.TabsPrizesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AvailableFragment: BaseFragment() {

    private lateinit var binding : FragmentAvailableBinding
    private val viewModel: TabsPrizesViewModel by sharedViewModel()

    companion object{

        const val PRIZES = "PRIZES"

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var prize: Prize? = null
        val bundle = this.arguments
        if (bundle != null) {
            prize = bundle.getSerializable(PRIZES) as Prize
            viewModel.savePrize(prize = prize)
        }

        var adapterPrizes: AdapterPrizes? = null
        viewModel.prizeAvailableLiveData.observe(viewLifecycleOwner, Observer {
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

}