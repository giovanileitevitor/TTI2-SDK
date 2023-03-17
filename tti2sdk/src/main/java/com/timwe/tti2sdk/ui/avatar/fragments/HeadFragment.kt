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
import com.timwe.tti2sdk.ui.avatar.fragments.adapters.AdapterGeneric
import com.timwe.tti2sdk.ui.avatar.fragments.viewmodel.FragmentsViewModel
import com.timwe.tti2sdk.ui.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeadFragment: BaseFragment() {

    private lateinit var binding : FragmentHeadBinding
    private val viewModel: FragmentsViewModel by viewModel()

    companion object{

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
            viewModel.saveAvatar(avatar = avatar)
        }

         // set name avatar
        viewModel.nameProfile.observe(viewLifecycleOwner, Observer {
            binding.textViewNameAvatar.visibility = View.VISIBLE
            binding.editTextName.visibility = View.VISIBLE
            binding.editTextCount.visibility = View.VISIBLE
            binding.editTextName.text = Editable.Factory.getInstance().newEditable(it)

            // set count avatar
            binding.editTextCount.text = "${it.length}/64"
        })

        binding.editTextName.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val length: Int =  binding.editTextName.text.length
                val convert = length.toString()
                binding.editTextCount.text = "$convert/64"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) { }

        })

        // set adapter gender
        var adapterGenericGender: AdapterGeneric? = null
        viewModel.resultForRecyclerViewGender.observe(viewLifecycleOwner, Observer { it ->
            if(it?.firstLoad!!){
                binding.textGender.visibility = View.VISIBLE
                adapterGenericGender = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = GENDER_VIEW_HOLDER,
                    positionSelected = it.positionSelected,
                ){ positionClicked ->
                    adapterGenericGender?.setNewPositionClicked(positionClicked)
                    viewModel.setTabHead(avatar!!, positionClicked)
                }
                binding.recyclerViewGender.adapter = adapterGenericGender
            }
        })

        // set skin color
        var adapterGenericForSkinColor: AdapterGeneric? = null
        viewModel.resultForRecyclerViewSkinColor.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                binding.textSkinColor.visibility = View.VISIBLE
                adapterGenericForSkinColor = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar_generic,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = CUSTON_VIEW_HOLDER,
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericForSkinColor?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewSkinColor.adapter = adapterGenericForSkinColor
            }else{
                adapterGenericForSkinColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
            }
        })

        // set hair and color hair
        var adapterGenericForHair: AdapterGeneric? = null
        viewModel.resultForRecyclerViewHair.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                binding.textSkinHair.visibility = View.VISIBLE
                adapterGenericForHair = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar_generic,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = CUSTON_VIEW_HOLDER,
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericForHair?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewHair.adapter = adapterGenericForHair
            }else{
                adapterGenericForHair?.setNewOptionsPosition(it.positionSelected, it.listOptions)
            }
        })
        var adapterGenericColorHair: AdapterGeneric? = null
        viewModel.resultForRecyclerViewHairColor.observe(viewLifecycleOwner, Observer {
            if(it?.firstLoad!!){
                adapterGenericColorHair = AdapterGeneric(
                    context = requireContext(),
                    resource = R.layout.item_list_avatar_generic,
                    data = it.listOptions,
                    mGlide = Glide.with(this),
                    typeViewHolder = CUSTON_VIEW_HOLDER,
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericColorHair?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewColorHair.adapter = adapterGenericColorHair
            }else{
                adapterGenericColorHair?.setNewOptionsPosition(it.positionSelected, it.listOptions)
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
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericEyeColor?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewEyeColor.adapter = adapterGenericEyeColor
            }else{
                adapterGenericEyeColor?.setNewOptionsPosition(it.positionSelected, it.listOptions)
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
                    positionSelected = it.positionSelected
                ){ positionClicked ->
                    adapterGenericColorBrows?.setNewPositionClicked(positionClicked)
                }
                binding.recyclerViewEyebrows.adapter = adapterGenericColorBrows
            }else{
                adapterGenericColorBrows?.setNewOptionsPosition(it.positionSelected, it.listOptions)
            }
        })

        viewModel.setTabHead(avatar!!)

    }

}