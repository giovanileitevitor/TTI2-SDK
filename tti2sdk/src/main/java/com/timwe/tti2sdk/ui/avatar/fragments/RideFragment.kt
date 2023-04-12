package com.timwe.tti2sdk.ui.avatar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.databinding.FragmentRideBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.GENDER_VIEW_HOLDER
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.RIDES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.RIDES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.adapters.AdapterGeneric
import com.timwe.tti2sdk.ui.avatar.fragments.viewmodel.TabsViewModel
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RideFragment: BaseFragment() {

    private lateinit var binding : FragmentRideBinding
    private val viewModel: TabsViewModel by sharedViewModel()

    companion object RideStats {
        fun newInstance(): RideFragment {
            return RideFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var avatar: Avatar? = null
        val bundle = this.arguments
        if (bundle != null) {
            avatar = bundle.getSerializable(HeadFragment.AVATAR) as Avatar
        }

        var adapterGenericRides: AdapterGeneric? = null
        viewModel.resultForRecyclerViewRides.observe(viewLifecycleOwner, Observer {
            if(adapterGenericRides == null){
                binding.textViewNameList.visibility = View.VISIBLE
                adapterGenericRides = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericRides?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = RIDES,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewRides.adapter = adapterGenericRides
            }else{
                adapterGenericRides?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = RIDES,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        var adapterGenericRidesColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewRidesColor.observe(viewLifecycleOwner, Observer {
            if(adapterGenericRidesColor == null){
                adapterGenericRidesColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericRidesColor?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = RIDES_COLOR,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewRidesColor.adapter = adapterGenericRidesColor
            }else{
                adapterGenericRidesColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = RIDES_COLOR,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        binding.btnSaveChanges.setOnClickListener {
            ((activity) as AvatarActivity).saveAvatarEdited()
        }

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible){
            viewModel.setTabRides()
        }
    }

}