package com.timwe.tti2sdk.ui.avatar.fragments.viewmodel

import androidx.lifecycle.*
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.CombinedResultForRecyclerView
import com.timwe.tti2sdk.data.model.response.AvatarCustomizationsResponse
import com.timwe.tti2sdk.data.model.response.ItemCustomizations
import com.timwe.tti2sdk.data.model.response.Options
import com.timwe.tti2sdk.domain.AvatarUseCase
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.BOTTOM_CLOTHES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.BOTTOM_CLOTHES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.GENDER
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_EYE_BROWS
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_EYE_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_HAIR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_HAIR_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.HEAD_SKIN_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.PROFILE_NAME
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.RIDES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.RIDES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.SHOES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.SHOES_COLOR
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.TOP_CLOTHES
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment.Companion.TOP_CLOTHES_COLOR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TabsViewModel(
    private val avatarUseCase: AvatarUseCase
): ViewModel() {

    //Head
    private val _nameProfile = MutableLiveData<String>()
    val nameProfile: LiveData<String> get() = _nameProfile

    private val _resultForRecyclerViewGender = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewGender: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewGender

    private val _resultForRecyclerViewSkinColor = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewSkinColor: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewSkinColor

    private val _resultForRecyclerViewHair = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewHair: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewHair

    private val _resultForRecyclerViewHairColor = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewHairColor: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewHairColor

    private val _resultForRecyclerViewEyeColor = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewEyeColor: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewEyeColor

    private val _resultForRecyclerViewEyebrows = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewEyeColorbrows: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewEyebrows

    //Clothes
    private val _resultForRecyclerViewTop = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewTop: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewTop

    private val _resultForRecyclerViewTopColor = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewTopColor: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewTopColor

    private val _resultForRecyclerViewBottoms = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewBottoms: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewBottoms

    private val _resultForRecyclerViewBottomsColor = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewBottomsColor: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewBottomsColor

    //Shoes
    private val _resultForRecyclerViewShoes = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewShoes: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewShoes

    private val _resultForRecyclerViewShoesColor = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewShoesColor: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewShoesColor

    //Rides
    private val _resultForRecyclerViewRides = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewRides: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewRides

    private val _resultForRecyclerViewRidesColor = MutableLiveData<CombinedResultForRecyclerView>()
    val resultForRecyclerViewRidesColor: LiveData<CombinedResultForRecyclerView> get() = _resultForRecyclerViewRidesColor

    private lateinit var avatarMale: Avatar
    private lateinit var avatarFemale: Avatar
    private lateinit var pureAvatar: Avatar
    private lateinit var gender: String
    private var pureGender: String? = null

    fun setTabHead(positionClicked: Int? = null){

        //tab Head
        val avatarCustomizationsResponseProfileName = filterCustomizationsByKey(key = PROFILE_NAME, avatarCustomizationsResponse = pureAvatar.headCustomizations)

        val avatarCustomizationsResponseGender = filterCustomizationsByKey(key = GENDER, avatarCustomizationsResponse = pureAvatar.headCustomizations, checkTags = false)

        val indexGender = positionClicked ?:  getInitialPosition(avatarCustomizationsResponse = pureAvatar.headCustomizations,
                                                                key = GENDER,
                                                                id = pureAvatar.skinColor.id)
        
        val genderInit = avatarCustomizationsResponseGender.first().options[indexGender].criteria

        gender = genderInit

        if(pureGender == null){
            pureGender = genderInit
        }

        val avatarAux: Avatar? = getAvatar(genderInit)

        val avatarCustomizationsResponseSkinColor = filterCustomizationsByKey(HEAD_SKIN_COLOR, gender=gender, avatarAux?.headCustomizations!!)
        val avatarCustomizationsResponseHair = filterCustomizationsByKey(HEAD_HAIR, gender=gender, avatarAux?.headCustomizations!!)
        val avatarCustomizationsResponseHairColor = filterCustomizationsByKey(HEAD_HAIR_COLOR, gender=gender, avatarAux?.headCustomizations!!, checkTags = false)
        val avatarCustomizationsResponseEyeColor = filterCustomizationsByKey(HEAD_EYE_COLOR, gender=gender, avatarAux?.headCustomizations!!, checkTags = false)
        val avatarCustomizationsResponseEyeBrows = filterCustomizationsByKey(HEAD_EYE_BROWS, gender=gender, avatarAux?.headCustomizations!!,checkTags = false)
        
        viewModelScope.launch(Dispatchers.IO){
            _nameProfile.postValue(pureAvatar.profileName)

            if(!avatarCustomizationsResponseGender.isNullOrEmpty()){
                _resultForRecyclerViewGender.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.headCustomizations,
                        key = GENDER,
                        id = avatarAux.skinColor.id
                    ),
                    listOptions = avatarCustomizationsResponseGender.first().options,
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = if (gender == "MALE") "Male" else "Female",
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseSkinColor.isNullOrEmpty()){
                _resultForRecyclerViewSkinColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.headCustomizations,
                        key = HEAD_SKIN_COLOR,
                        id = avatarAux.skinColor.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseSkinColor.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseSkinColor.first().options)
                )
            }

            if(!avatarCustomizationsResponseHair.isNullOrEmpty()){
                _resultForRecyclerViewHair.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.headCustomizations,
                        key = HEAD_HAIR,
                        id = avatarAux.hair.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseHair.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseHair.first().options)
                )
            }

            if(!avatarCustomizationsResponseHairColor.isNullOrEmpty()){
                _resultForRecyclerViewHairColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.headCustomizations,
                        key = HEAD_HAIR_COLOR,
                        id = avatarAux.hairColor.id
                    ),
                    listOptions = avatarCustomizationsResponseHairColor.first().options,
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseHairColor.first().riveInputKey,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseEyeColor.isNullOrEmpty()){
                _resultForRecyclerViewEyeColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.headCustomizations,
                        key = HEAD_EYE_COLOR,
                        id = avatarAux.eyeColor.id
                    ),
                    listOptions = avatarCustomizationsResponseEyeColor.first().options,
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseEyeColor.first().riveInputKey,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseEyeBrows.isNullOrEmpty()){
                _resultForRecyclerViewEyebrows.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.headCustomizations,
                        key = HEAD_EYE_BROWS,
                        id = avatarAux.eyeBrows.id
                    ),
                    listOptions = avatarCustomizationsResponseEyeBrows.first().options,
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseEyeBrows.first().riveInputKey,
                    firstLoad = positionClicked == null)
                )
            }

        }

    }

    fun setTabClothes(){

        val avatarAux = getAvatar(gender)
        
        //tab clothes
        val avatarCustomizationsResponseTop = filterCustomizationsByKey(TOP_CLOTHES, gender=gender, avatarAux?.clothesCustomizations!!)
        val avatarCustomizationsResponseTopColor = filterCustomizationsByKey(TOP_CLOTHES_COLOR, gender=gender, avatarAux?.clothesCustomizations!!, checkTags = false)
        val avatarCustomizationsResponseBottoms = filterCustomizationsByKey(BOTTOM_CLOTHES, gender=gender, avatarAux?.clothesCustomizations!!)
        val avatarCustomizationsResponseBottomsColor = filterCustomizationsByKey(BOTTOM_CLOTHES_COLOR, gender=gender, avatarAux?.clothesCustomizations!!, checkTags = false)

        viewModelScope.launch(Dispatchers.IO){

            if(!avatarCustomizationsResponseTop.isNullOrEmpty()){
                _resultForRecyclerViewTop.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.clothesCustomizations,
                        key = TOP_CLOTHES,
                        id = avatarAux.topClothes.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseTop.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseTop.first().options)
                )
            }

            if(!avatarCustomizationsResponseTopColor.isNullOrEmpty()){
                _resultForRecyclerViewTopColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.clothesCustomizations,
                        key = TOP_CLOTHES_COLOR,
                        id = avatarAux.topClothesColor.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseTopColor.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseTopColor.first().options)
                )
            }

            if(!avatarCustomizationsResponseBottoms.isNullOrEmpty()){
                _resultForRecyclerViewBottoms.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.clothesCustomizations,
                        key = BOTTOM_CLOTHES,
                        id = avatarAux.bottomClothes.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseBottoms.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseBottoms.first().options)
                )
            }

            if(!avatarCustomizationsResponseBottomsColor.isNullOrEmpty()){
                _resultForRecyclerViewBottomsColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.clothesCustomizations,
                        key = BOTTOM_CLOTHES_COLOR,
                        id = avatarAux.bottomClothesColor.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseBottomsColor.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseBottomsColor.first().options)
                )
            }

        }
    }

    fun setTabShoes(){

        val avatarAux = getAvatar(gender)

        //tab shoes
        val avatarCustomizationsResponseShoes = filterCustomizationsByKey(SHOES, gender=gender, avatarAux?.shoesCustomizations!!)
        val avatarCustomizationsResponseShoesColor = filterCustomizationsByKey(SHOES_COLOR, gender=gender, avatarAux?.shoesCustomizations!!, checkTags = false)

        viewModelScope.launch(Dispatchers.IO){

            // Shoes
            if(!avatarCustomizationsResponseShoes .isNullOrEmpty()){
                _resultForRecyclerViewShoes.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.shoesCustomizations,
                        key = SHOES,
                        id = avatarAux.shoes.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseShoes.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseShoes.first().options)
                )
            }

            if(!avatarCustomizationsResponseShoesColor .isNullOrEmpty()){
                _resultForRecyclerViewShoesColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.shoesCustomizations,
                        key = SHOES_COLOR,
                        id = avatarAux.shoesColor.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseShoesColor.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseShoesColor.first().options)
                )
            }

        }
    }

    fun setTabRides(){

        val avatarAux = getAvatar(gender)

        //tab rides
        val avatarCustomizationsResponseRides = filterCustomizationsByKey(RIDES, gender=gender, avatarAux?.ridesCustomizations!!)
        val avatarCustomizationsResponseRidesColor = filterCustomizationsByKey(RIDES_COLOR, gender=gender, avatarAux?.ridesCustomizations!!, checkTags = false)
        
        viewModelScope.launch(Dispatchers.IO){

            // Rides
            if(!avatarCustomizationsResponseRides.isNullOrEmpty()){
                _resultForRecyclerViewRides.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.ridesCustomizations,
                        key = RIDES,
                        id = avatarAux.rides.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseRides.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseRides.first().options)
                )
            }

            if(!avatarCustomizationsResponseRidesColor.isNullOrEmpty()){
                _resultForRecyclerViewRidesColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getPositionSet(
                        avatarCustomizationsResponse = avatarAux.ridesCustomizations,
                        key = RIDES_COLOR,
                        id = avatarAux.ridesColor.id
                    ),
                    riveInputGender = if (gender == "MALE") "Male" else "Female",
                    riveInputKey = avatarCustomizationsResponseRidesColor.first().riveInputKey,
                    listOptions = avatarCustomizationsResponseRidesColor.first().options)
                )
            }

        }
    }

    fun getInitialPosition(avatarCustomizationsResponse: AvatarCustomizationsResponse, key: String, id: Int): Int{
        val listOptions = avatarCustomizationsResponse.customizations.filter {
            it.key == key
        }
        listOptions.first().options.forEachIndexed{ i, it ->
            if(it.id == id){
                return i
            }
        }
        return 0
    }

    private fun getPositionSet(avatarCustomizationsResponse: AvatarCustomizationsResponse, key: String, id: Int): Int{
       if(pureGender == gender){
            return getInitialPosition(avatarCustomizationsResponse, key, id)
        }else{
           return 0
        }
    }

    private fun filterCustomizationsByKey(key: String, gender: String? = null, avatarCustomizationsResponse: AvatarCustomizationsResponse, checkTags: Boolean = true): List<ItemCustomizations> {
        return try {

            val myAvatarCustomizationsResponse = avatarCustomizationsResponse.customizations.filter {
                it.key == key
            }.first()

            myAvatarCustomizationsResponse.options = filterOptionsByGender(gender, myAvatarCustomizationsResponse.options, checkTags)

            listOf(myAvatarCustomizationsResponse)

        }catch (e: java.lang.Exception){
            listOf()
        }

    }

    private fun filterOptionsByGender(gender: String? = null, options: List<Options>? = null, checkTags: Boolean = true): List<Options> {
        try {
            if (!checkTags){
                return options!!
            }

            val filteredOptions = options?.filter{ item ->
                ( item.gender.equals(gender) || item.gender.equals("BOTH") )
            }
            return filteredOptions!!

        }catch (e: java.lang.Exception){
            return listOf()
        }

    }

    fun saveAvatar(avatar: Avatar) {
        avatarMale = avatar.clone()
        avatarFemale = avatar.clone()
        pureAvatar = avatar.clone()
    }

    fun getAvatar(gender: String): Avatar? {
        var avatarAux: Avatar? =  null
        if (gender == "MALE") {
            avatarMale.let {
                avatarAux = it
            }
        } else if(gender == "FEMALE"){
            avatarFemale.let {
                avatarAux = it
            }
        }else{
            throw Exception("Gender not exists")
        }
        return avatarAux
    }

}