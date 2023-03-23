package com.timwe.tti2sdk.ui.avatar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.databinding.FragmentClothesBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.BOTTOM_CLOTHES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.BOTTOM_CLOTHES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.GENDER_VIEW_HOLDER
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.TOP_CLOTHES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.TOP_CLOTHES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.adapters.AdapterGeneric
import com.timwe.tti2sdk.ui.avatar.fragments.viewmodel.TabsViewModel
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ClothesFragment: BaseFragment() {

    private lateinit var binding : FragmentClothesBinding
    private val viewModel: TabsViewModel by sharedViewModel()

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
            if(adapterGenericTop == null){
                binding.textViewNameListTop.visibility = View.VISIBLE
                adapterGenericTop = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericTop?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = TOP_CLOTHES,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewTop.adapter = adapterGenericTop
            }else{
                adapterGenericTop?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = TOP_CLOTHES,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        var adapterGenericTopColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewTopColor.observe(viewLifecycleOwner, Observer {
            if(adapterGenericTopColor == null){
                adapterGenericTopColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericTopColor?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = TOP_CLOTHES_COLOR,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewTopColor.adapter = adapterGenericTopColor
            }else{
                adapterGenericTopColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = TOP_CLOTHES_COLOR,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        var adapterGenericBottoms: AdapterGeneric? = null
        viewModel.resultForRecyclerViewBottoms.observe(viewLifecycleOwner, Observer {
            if(adapterGenericBottoms == null){
                binding.textBottoms.visibility = View.VISIBLE
                adapterGenericBottoms = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericBottoms?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = BOTTOM_CLOTHES,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewBottoms.adapter = adapterGenericBottoms
            }else{
                adapterGenericBottoms?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = BOTTOM_CLOTHES,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        var adapterGenericBottomsColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewBottomsColor.observe(viewLifecycleOwner, Observer {
            if(adapterGenericBottomsColor == null){
                adapterGenericBottomsColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericBottomsColor?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = BOTTOM_CLOTHES_COLOR,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewBottomsColor.adapter = adapterGenericBottomsColor
            }else{
                adapterGenericBottomsColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = BOTTOM_CLOTHES_COLOR,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if(menuVisible){
            viewModel.setTabClothes()
        }
    }

}