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
            if(it?.firstLoad!!){
                binding.textViewNameList.visibility = View.VISIBLE
                adapterGenericRides = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = HeadFragment.GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericRides?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                }
                binding.recyclerViewStyle.adapter = adapterGenericRides
            }else{
                adapterGenericRides?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions.first().riveInputValue
                )
            }
        })

        var adapterGenericRidesColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewRidesColor.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                adapterGenericRidesColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = HeadFragment.GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericRidesColor?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                }
                binding.recyclerViewStyleColor.adapter = adapterGenericRidesColor
            }else{
                adapterGenericRidesColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions.first().riveInputValue
                )
            }
        })

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible){
            viewModel.setTabRides()
        }
    }

}