package com.timwe.tti2sdk.ui.avatar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.databinding.FragmentShoesBinding
import com.timwe.tti2sdk.ui.avatar.fragments.adapters.AdapterGeneric
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoesFragment: BaseFragment() {

    private lateinit var binding : FragmentShoesBinding
    private val viewModel: FragmentsViewModel by viewModel()

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
        binding = FragmentShoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var avatar: Avatar? = null
        val bundle = this.arguments
        if (bundle != null) {
            avatar = bundle.getSerializable(HeadFragment.AVATAR) as Avatar
        }

        var adapterGenericShoes: AdapterGeneric? = null
        viewModel.resultForRecyclerViewShoes.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                binding.textViewNameList.visibility = View.VISIBLE
                adapterGenericShoes = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = HeadFragment.GENDER_VIEW_HOLDER,
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericShoes?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewShoes.adapter = adapterGenericShoes
            }else{
                adapterGenericShoes?.setNewOptionsPosition(it.positionSelected, it.listOptions)
            }
        })

        var adapterGenericShoesColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewShoesColor.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                adapterGenericShoesColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = HeadFragment.GENDER_VIEW_HOLDER,
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericShoesColor?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewShoesColor.adapter = adapterGenericShoesColor
            }else{
                adapterGenericShoesColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
            }
        })

        viewModel.setTabShoes(avatar!!)
    }

}