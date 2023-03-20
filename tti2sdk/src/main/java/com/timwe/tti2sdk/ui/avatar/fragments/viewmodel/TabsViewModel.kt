package com.timwe.tti2sdk.ui.avatar.fragments.viewmodel

import androidx.lifecycle.*
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.CombinedResultForRecyclerView
import com.timwe.tti2sdk.data.model.response.AvatarCustomizationsResponse
import com.timwe.tti2sdk.data.model.response.ItemCustomizations
import com.timwe.tti2sdk.data.model.response.Options
import com.timwe.tti2sdk.domain.AvatarUseCase
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment
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
        val avatarCustomizationsResponseProfileName = filterCustomizationsByKey(key = HeadFragment.PROFILE_NAME, avatarCustomizationsResponse = pureAvatar.headCustomizations)

        val avatarCustomizationsResponseGender = filterCustomizationsByKey(key = HeadFragment.GENDER, avatarCustomizationsResponse = pureAvatar.headCustomizations, checkTags = false)

        val indexGender = positionClicked ?: avatarCustomizationsResponseGender.first().userOptionIdx
        val genderInit = avatarCustomizationsResponseGender.first().options[indexGender].criteria

        gender = genderInit

        if(pureGender == null){
            pureGender = genderInit
        }

        val avatarAux: Avatar? = getAvatar(genderInit)

        val avatarCustomizationsResponseSkinColor = filterCustomizationsByKey(HeadFragment.HEAD_SKIN_COLOR, gender=gender, avatarAux?.headCustomizations!!)
        val avatarCustomizationsResponseHair = filterCustomizationsByKey(HeadFragment.HEAD_HAIR, gender=gender, avatarAux?.headCustomizations!!)
        val avatarCustomizationsResponseHairColor = filterCustomizationsByKey(HeadFragment.HEAD_HAIR_COLOR, gender=gender, avatarAux?.headCustomizations!!, checkTags = false)
        val avatarCustomizationsResponseEyeColor = filterCustomizationsByKey(HeadFragment.HEAD_EYE_COLOR, gender=gender, avatarAux?.headCustomizations!!, checkTags = false)
        val avatarCustomizationsResponseEyeBrows = filterCustomizationsByKey(HeadFragment.HEAD_EYE_BROWS, gender=gender, avatarAux?.headCustomizations!!,checkTags = false)
        
        viewModelScope.launch(Dispatchers.IO){
            _nameProfile.postValue(avatarCustomizationsResponseProfileName.first().label)

            if(!avatarCustomizationsResponseGender.isNullOrEmpty()){
                _resultForRecyclerViewGender.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseGender.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseGender.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseSkinColor.isNullOrEmpty()){
                _resultForRecyclerViewSkinColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseSkinColor.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseSkinColor.first().options)
                )
            }

            if(!avatarCustomizationsResponseHair.isNullOrEmpty()){
                _resultForRecyclerViewHair.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseHair.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseHair.first().options)
                )
            }

            if(!avatarCustomizationsResponseHairColor.isNullOrEmpty()){
                _resultForRecyclerViewHairColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseHairColor.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseHairColor.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseEyeColor.isNullOrEmpty()){
                _resultForRecyclerViewEyeColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseEyeColor.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseEyeColor.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseEyeBrows.isNullOrEmpty()){
                _resultForRecyclerViewEyebrows.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseEyeBrows.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseEyeBrows.first().options,
                    firstLoad = positionClicked == null)
                )
            }

        }

    }

    fun setTabClothes(){

        val avatarAux = getAvatar(gender)
        
        //tab clothes
        val avatarCustomizationsResponseTop = filterCustomizationsByKey(HeadFragment.TOP_CLOTHES, gender=gender, avatarAux?.clothesCustomizations!!)
        val avatarCustomizationsResponseTopColor = filterCustomizationsByKey(HeadFragment.TOP_CLOTHES_COLOR, gender=gender, avatarAux?.clothesCustomizations!!, checkTags = false)
        val avatarCustomizationsResponseBottoms = filterCustomizationsByKey(HeadFragment.BOTTOM_CLOTHES, gender=gender, avatarAux?.clothesCustomizations!!)
        val avatarCustomizationsResponseBottomsColor = filterCustomizationsByKey(HeadFragment.BOTTOM_CLOTHES_COLOR, gender=gender, avatarAux?.clothesCustomizations!!, checkTags = false)

        viewModelScope.launch(Dispatchers.IO){

            if(!avatarCustomizationsResponseTop.isNullOrEmpty()){
                _resultForRecyclerViewTop.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseTop.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseTop.first().options)
                )
            }

            if(!avatarCustomizationsResponseTopColor.isNullOrEmpty()){
                _resultForRecyclerViewTopColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseTopColor.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseTopColor.first().options)
                )
            }

            if(!avatarCustomizationsResponseBottoms.isNullOrEmpty()){
                _resultForRecyclerViewBottoms.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseBottoms.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseBottoms.first().options)
                )
            }

            if(!avatarCustomizationsResponseBottomsColor.isNullOrEmpty()){
                _resultForRecyclerViewBottomsColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseBottomsColor.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseBottomsColor.first().options)
                )
            }

        }
    }

    fun setTabShoes(){

        val avatarAux = getAvatar(gender)

        //tab shoes
        val avatarCustomizationsResponseShoes = filterCustomizationsByKey(HeadFragment.SHOES, gender=gender, avatarAux?.shoesCustomizations!!)
        val avatarCustomizationsResponseShoesColor = filterCustomizationsByKey(HeadFragment.SHOES_COLOR, gender=gender, avatarAux?.shoesCustomizations!!, checkTags = false)

        viewModelScope.launch(Dispatchers.IO){

            // Shoes
            if(!avatarCustomizationsResponseShoes .isNullOrEmpty()){
                _resultForRecyclerViewShoes.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseShoes.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseShoes.first().options)
                )
            }

            if(!avatarCustomizationsResponseShoesColor .isNullOrEmpty()){
                _resultForRecyclerViewShoesColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseShoesColor.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseShoesColor.first().options)
                )
            }

        }
    }

    fun setTabRides(){

        val avatarAux = getAvatar(gender)

        //tab rides
        val avatarCustomizationsResponseRides = filterCustomizationsByKey(HeadFragment.RIDES, gender=gender, avatarAux?.ridesCustomizations!!)
        val avatarCustomizationsResponseRidesColor = filterCustomizationsByKey(HeadFragment.RIDES_COLOR, gender=gender, avatarAux?.ridesCustomizations!!, checkTags = false)
        
        viewModelScope.launch(Dispatchers.IO){

            // Rides
            if(!avatarCustomizationsResponseRides.isNullOrEmpty()){
                _resultForRecyclerViewRides.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseRides.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseRides.first().options)
                )
            }

            if(!avatarCustomizationsResponseRidesColor.isNullOrEmpty()){
                _resultForRecyclerViewRidesColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = getIndexClicked(avatarCustomizationsResponseRidesColor.first().userOptionIdx),
                    listOptions = avatarCustomizationsResponseRidesColor.first().options)
                )
            }

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

    private fun getAvatar(gender: String): Avatar? {
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

    private fun getIndexClicked(pureIndex: Int): Int{
        return if(pureGender == gender){
            pureIndex
        }else{
            0
        }
    }


}