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
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.GENDER_VIEW_HOLDER
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.SHOES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.SHOES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.adapters.AdapterGeneric
import com.timwe.tti2sdk.ui.avatar.fragments.viewmodel.TabsViewModel
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ShoesFragment: BaseFragment() {

    private lateinit var binding : FragmentShoesBinding
    private val viewModel: TabsViewModel by sharedViewModel()

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
            if(adapterGenericShoes == null){
                binding.textViewNameList.visibility = View.VISIBLE
                adapterGenericShoes = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericShoes?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = SHOES,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewShoes.adapter = adapterGenericShoes
            }else{
                adapterGenericShoes?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions.first().riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = SHOES,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        var adapterGenericShoesColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewShoesColor.observe(viewLifecycleOwner, Observer {
            if(adapterGenericShoesColor == null){
                adapterGenericShoesColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericShoesColor?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = SHOES_COLOR,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewShoesColor.adapter = adapterGenericShoesColor
            }else{
                adapterGenericShoesColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions.first().riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = SHOES_COLOR,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if(menuVisible){
            viewModel.setTabShoes()
        }
    }

}