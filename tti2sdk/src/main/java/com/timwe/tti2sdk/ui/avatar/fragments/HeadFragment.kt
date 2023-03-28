package com.timwe.tti2sdk.ui.avatar.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.databinding.FragmentHeadBinding
import com.timwe.tti2sdk.ui.avatar.AvatarActivity
import com.timwe.tti2sdk.ui.avatar.fragments.adapters.AdapterGeneric
import com.timwe.tti2sdk.ui.avatar.fragments.viewmodel.TabsViewModel
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HeadFragment: BaseFragment() {

    private lateinit var binding : FragmentHeadBinding
    private val viewModel: TabsViewModel by sharedViewModel()

    companion object{

        const val AVATAR = "AVATAR"
        const val AVATAR_IMAGE = "AVATAR_IMAGE"
        const val AVATAR_LIST = "AVATAR_LIST"
        const val GENDER_VIEW_HOLDER = 0
        const val CUSTON_VIEW_HOLDER = 1

        const val GENDER_MALE = "MALE"
        const val GENDER_FEMALE = "FEMALE"

        const val PROFILE_NAME = "PROFILE_NAME"
        const val GENDER = "GENDER"
        const val HEAD_SKIN_COLOR = "SKIN_COLOR"
        const val HEAD_HAIR = "HAIR"
        const val HEAD_HAIR_COLOR = "HAIR_COLOR"
        const val HEAD_EYE_COLOR = "EYE_COLOR"
        const val HEAD_EYE_BROWS = "EYEBROWS"

        const val TOP_CLOTHES = "TOP_CLOTHES"
        const val TOP_CLOTHES_COLOR = "TOP_CLOTHES_COLOR"
        const val BOTTOM_CLOTHES = "BOTTOM_CLOTHES"
        const val BOTTOM_CLOTHES_COLOR = "BOTTOM_CLOTHES_COLOR"

        const val SHOES = "SHOES"
        const val SHOES_COLOR = "SHOES_COLOR"

        const val RIDES = "RIDES"
        const val RIDES_COLOR = "RIDES_COLOR"

        fun newInstance(): HeadFragment {
            return HeadFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeadBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var avatar: Avatar? = null
        val bundle = this.arguments
        if (bundle != null) {
            avatar = bundle.getSerializable(AVATAR) as Avatar
            viewModel.saveAvatar(avatar = avatar)
        }

         // set name avatar
        viewModel.nameProfile.observe(viewLifecycleOwner, Observer {
            binding.textViewNameAvatar.visibility = View.VISIBLE
            binding.editTextName.visibility = View.VISIBLE
            binding.editTextCount.visibility = View.VISIBLE
            binding.editTextName.text = Editable.Factory.getInstance().newEditable(it)
            ((activity) as AvatarActivity).setAvatarEdited(key = PROFILE_NAME, value = it)

            // set count avatar
            binding.editTextCount.text = "${it.length}/64"
        })

        binding.editTextName.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val length: Int =  binding.editTextName.text.length
                val convert = length.toString()
                binding.editTextCount.text = "$convert/64"
                ((activity) as AvatarActivity).setAvatarEdited(key = PROFILE_NAME, value = binding.editTextName.text.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) { }

        })

        // set adapter gender
        var adapterGenericGender: AdapterGeneric? = null
        viewModel.resultForRecyclerViewGender.observe(viewLifecycleOwner, Observer { it ->
            if(adapterGenericGender == null){
                binding.textGender.visibility = View.VISIBLE
                adapterGenericGender = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected,
                ){ avatarSet ->
                    adapterGenericGender?.setNewPositionClicked(avatarSet.positionClick)
                    viewModel.setTabHead(avatarSet.positionClick)
                    ((activity) as AvatarActivity).mountAvatarImage(
                        avatar = viewModel.getAvatar(gender = it.listOptions[it.positionSelected].criteria)!!
                    )
                }
                binding.recyclerViewGender.adapter = adapterGenericGender
            }
        })

        // set skin color
        var adapterGenericForSkinColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewSkinColor.observe(viewLifecycleOwner, Observer {
            if(adapterGenericForSkinColor == null){
                binding.textSkinColor.visibility = View.VISIBLE
                adapterGenericForSkinColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar_generic,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = CUSTON_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericForSkinColor?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                            inputValueKey = avatarSet.riveInputKey,
                            inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = HEAD_SKIN_COLOR,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewSkinColor.adapter = adapterGenericForSkinColor
            }else{
                adapterGenericForSkinColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = HEAD_SKIN_COLOR,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        // set hair and color hair
        var adapterGenericForHair: AdapterGeneric? = null
        viewModel.resultForRecyclerViewHair.observe(viewLifecycleOwner, Observer {
            if(adapterGenericForHair == null){
                binding.textSkinHair.visibility = View.VISIBLE
                adapterGenericForHair = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar_generic,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = CUSTON_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericForHair?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = HEAD_HAIR,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewHair.adapter = adapterGenericForHair
            }else{
                adapterGenericForHair?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = HEAD_HAIR,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })
        var adapterGenericColorHair: AdapterGeneric? = null
        viewModel.resultForRecyclerViewHairColor.observe(viewLifecycleOwner, Observer {
            if(adapterGenericColorHair == null){
                adapterGenericColorHair = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar_generic,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = CUSTON_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericColorHair?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = HEAD_HAIR_COLOR,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewColorHair.adapter = adapterGenericColorHair
            }else{
                adapterGenericColorHair?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = HEAD_HAIR_COLOR,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        // set eye color
        var adapterGenericEyeColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewEyeColor.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                binding.textEyeColor.visibility = View.VISIBLE
                adapterGenericEyeColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar_generic,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = CUSTON_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericEyeColor?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = HEAD_EYE_COLOR,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewEyeColor.adapter = adapterGenericEyeColor
            }else{
                adapterGenericEyeColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = HEAD_EYE_COLOR,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        // set eye brows
        var adapterGenericColorBrows: AdapterGeneric? = null
        viewModel.resultForRecyclerViewEyeColorbrows.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                binding.textEyeBrows.visibility = View.VISIBLE
                adapterGenericColorBrows = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar_generic,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = CUSTON_VIEW_HOLDER,
                    riveInputKey = it.riveInputKey,
                    positionSelected = it.positionSelected
                ){ avatarSet ->
                    adapterGenericColorBrows?.setNewPositionClicked(avatarSet.positionClick)
                    ((activity) as AvatarActivity).setAvatar(
                        inputValueKey = avatarSet.riveInputKey,
                        inputValue = avatarSet.riveInputValue
                    )
                    ((activity) as AvatarActivity).setAvatarEdited(
                        key = HEAD_EYE_BROWS,
                        value = avatarSet.idForEditedAvatar
                    )
                }
                binding.recyclerViewEyebrows.adapter = adapterGenericColorBrows
            }else{
                adapterGenericColorBrows?.setNewOptionsPosition(it.positionSelected, it.listOptions)
                ((activity) as AvatarActivity).setAvatar(
                    inputValueKey = it.riveInputKey,
                    inputValue = it.listOptions[it.positionSelected].riveInputValue
                )
                ((activity) as AvatarActivity).setAvatarEdited(
                    key = HEAD_EYE_BROWS,
                    value = it.listOptions[it.positionSelected].id.toString()
                )
            }
        })

        binding.btnSaveChanges.setOnClickListener {
            ((activity) as AvatarActivity).saveAvatarEdited()
        }

        viewModel.setTabHead()

    }

}