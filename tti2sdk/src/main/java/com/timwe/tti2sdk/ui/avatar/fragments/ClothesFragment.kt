package com.timwe.tti2sdk.ui.avatar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.databinding.FragmentClothesBinding
import com.timwe.tti2sdk.ui.avatar.fragments.adapters.AdapterGeneric
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClothesFragment: BaseFragment() {

    private lateinit var binding : FragmentClothesBinding
    private val viewModel: FragmentsViewModel by viewModel()

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
        binding = FragmentClothesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var avatar: Avatar? = null
        val bundle = this.arguments
        if (bundle != null) {
            avatar = bundle.getSerializable(HeadFragment.AVATAR) as Avatar
        }

        var adapterGenericTop: AdapterGeneric? = null
        viewModel.resultForRecyclerViewTop.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                binding.textViewNameListTop.visibility = View.VISIBLE
                adapterGenericTop = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = HeadFragment.GENDER_VIEW_HOLDER,
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericTop?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewTop.adapter = adapterGenericTop
            }else{
                adapterGenericTop?.setNewOptionsPosition(it.positionSelected, it.listOptions)
            }
        })

        var adapterGenericTopColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewTopColor.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                adapterGenericTopColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = HeadFragment.GENDER_VIEW_HOLDER,
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericTopColor?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewTopColor.adapter = adapterGenericTopColor
            }else{
                adapterGenericTopColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
            }
        })

        var adapterGenericBottoms: AdapterGeneric? = null
        viewModel.resultForRecyclerViewBottoms.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                binding.textBottoms.visibility = View.VISIBLE
                adapterGenericBottoms = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = HeadFragment.GENDER_VIEW_HOLDER,
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericBottoms?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewBottoms.adapter = adapterGenericBottoms
            }else{
                adapterGenericBottoms?.setNewOptionsPosition(it.positionSelected, it.listOptions)
            }
        })

        var adapterGenericBottomsColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewBottomsColor.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                adapterGenericBottomsColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = HeadFragment.GENDER_VIEW_HOLDER,
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericBottoms?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewBottomsColor.adapter = adapterGenericBottomsColor
            }else{
                adapterGenericBottomsColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
            }
        })

        viewModel.setTabClothes(avatar!!)
    }

}