package com.timwe.tti2sdk.ui.avatar.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.databinding.FragmentHeadBinding
import com.timwe.tti2sdk.ui.avatar.fragments.adapters.AdapterGeneric
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeadFragment: BaseFragment() {

    private lateinit var binding : FragmentHeadBinding
    private val viewModel: FragmentsViewModel by viewModel()

    companion object {

        public const val AVATAR = "AVATAR"
        public const val AVATAR_IMAGE = "AVATAR_IMAGE"
        public const val AVATAR_LIST = "AVATAR_LIST"
        public const val GENDER_VIEW_HOLDER = 0
        public const val CUSTON_VIEW_HOLDER = 1

        public const val GENDER_MALE = "MALE"
        public const val GENDER_FEMALE = "FEMALE"

        public const val PROFILE_NAME = "PROFILE_NAME"
        public const val GENDER = "GENDER"
        public const val HEAD_SKIN_COLOR = "SKIN_COLOR"
        public const val HEAD_HAIR = "HAIR"
        public const val HEAD_HAIR_COLOR = "HAIR_COLOR"
        public const val HEAD_EYE_COLOR = "EYE_COLOR"
        public const val HEAD_EYE_BROWS = "EYEBROWS"

        public const val TOP_CLOTHES = "TOP_CLOTHES"
        public const val TOP_CLOTHES_COLOR = "TOP_CLOTHES_COLOR"
        public const val BOTTOM_CLOTHES = "BOTTOM_CLOTHES"
        public const val BOTTOM_CLOTHES_COLOR = "BOTTOM_CLOTHES_COLOR"

        public const val SHOES = "SHOES"
        public const val SHOES_COLOR = "SHOES_COLOR"

        public const val RIDES = "RIDES"
        public const val RIDES_COLOR = "RIDES_COLOR"

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
        }

         // set name avatar
        viewModel.nameProfile.observe(viewLifecycleOwner, Observer {
            binding.textViewNameAvatar.visibility = View.VISIBLE
            binding.editTextName.visibility = View.VISIBLE
            binding.editTextCount.visibility = View.VISIBLE
            binding.editTextName.text = Editable.Factory.getInstance().newEditable(it)
        })

        // set count avatar

        // set adapter gender
        viewModel.resultForRecyclerViewGender.observe(viewLifecycleOwner, Observer {
            binding.textGender.visibility = View.VISIBLE
            binding.recyclerViewGender.adapter = AdapterGeneric(
                context = requireContext(),
                resource = R.layout.item_list_avatar,
                data = it.listOptions,
                mGlide = Glide.with(this),
                typeViewHolder = GENDER_VIEW_HOLDER,
                positionSelected = it.positionSelected
            )
        })

        // set skin color
        viewModel.resultForRecyclerViewSkinColor.observe(viewLifecycleOwner, Observer {
            binding.textSkinColor.visibility = View.VISIBLE
            binding.recyclerViewSkinColor.adapter = AdapterGeneric(
                context = requireContext(),
                resource = R.layout.item_list_avatar_generic,
                data = it.listOptions,
                mGlide = Glide.with(this),
                typeViewHolder = CUSTON_VIEW_HOLDER,
                positionSelected = it.positionSelected
            )
        })

        // set hair and color hair
        viewModel.resultForRecyclerViewHair.observe(viewLifecycleOwner, Observer {
            binding.textSkinHair.visibility = View.VISIBLE
            binding.recyclerViewHair.adapter = AdapterGeneric(
                context = requireContext(),
                resource = R.layout.item_list_avatar_generic,
                data = it.listOptions,
                mGlide = Glide.with(this),
                typeViewHolder = CUSTON_VIEW_HOLDER,
                positionSelected = it.positionSelected
            )
        })
        viewModel.resultForRecyclerViewHairColor.observe(viewLifecycleOwner, Observer {
            binding.recyclerViewColorHair.adapter = AdapterGeneric(
                context = requireContext(),
                resource = R.layout.item_list_avatar_generic,
                data = it.listOptions,
                mGlide = Glide.with(this),
                typeViewHolder = CUSTON_VIEW_HOLDER,
                positionSelected = it.positionSelected
            )
        })

        // set eye color
        viewModel.resultForRecyclerViewEyeColor.observe(viewLifecycleOwner, Observer {
            binding.textEyeColor.visibility = View.VISIBLE
            binding.recyclerViewEyeColor.adapter = AdapterGeneric(
                context = requireContext(),
                resource = R.layout.item_list_avatar_generic,
                data = it.listOptions,
                mGlide = Glide.with(this),
                typeViewHolder = CUSTON_VIEW_HOLDER,
                positionSelected = it.positionSelected
            )
        })

        // set eye brows
        viewModel.resultForRecyclerViewEyeColorbrows.observe(viewLifecycleOwner, Observer {
            binding.textEyeBrows.visibility = View.VISIBLE
            binding.recyclerViewEyebrows.adapter = AdapterGeneric(
                context = requireContext(),
                resource = R.layout.item_list_avatar_generic,
                data = it.listOptions,
                mGlide = Glide.with(this),
                typeViewHolder = CUSTON_VIEW_HOLDER,
                positionSelected = it.positionSelected
            )
        })

        viewModel.setTabHead(avatar!!)

    }

}