package com.timwe.tti2sdk.ui.avatar.fragments

import androidx.lifecycle.*
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.CombinedResultForRecyclerView
import com.timwe.tti2sdk.data.model.response.AvatarCustomizationsResponse
import com.timwe.tti2sdk.data.model.response.ItemCustomizations
import com.timwe.tti2sdk.data.model.response.Options
import com.timwe.tti2sdk.data.net.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentsViewModel(
    private val repoRepository: RepoRepository
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

    fun setTabHead(avatar: Avatar, positionClicked: Int? = null){

        //tab Head
        val avatarCustomizationsResponseProfileName = filterCustomizationsByKey(key = HeadFragment.PROFILE_NAME, avatarCustomizationsResponse = avatar.headCustomizations)

        val avatarCustomizationsResponseGender = filterCustomizationsByKey(key = HeadFragment.GENDER, avatarCustomizationsResponse = avatar.headCustomizations, checkTags = false)

        val indexGender = positionClicked ?: avatarCustomizationsResponseGender.first().userOptionIdx
        val gender = avatarCustomizationsResponseGender.first().options[indexGender].criteria
        
        val avatarCustomizationsResponseSkinColor = filterCustomizationsByKey(HeadFragment.HEAD_SKIN_COLOR, gender=gender, avatar.headCustomizations)
        val avatarCustomizationsResponseHair = filterCustomizationsByKey(HeadFragment.HEAD_HAIR, gender=gender, avatar.headCustomizations)
        val avatarCustomizationsResponseHairColor = filterCustomizationsByKey(HeadFragment.HEAD_HAIR_COLOR, gender=gender, avatar.headCustomizations, checkTags = false)
        val avatarCustomizationsResponseEyeColor = filterCustomizationsByKey(HeadFragment.HEAD_EYE_COLOR, gender=gender, avatar.headCustomizations, checkTags = false)
        val avatarCustomizationsResponseEyeBrows = filterCustomizationsByKey(HeadFragment.HEAD_EYE_BROWS, gender=gender, avatar.headCustomizations,checkTags = false)
        
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
                    positionSelected = avatarCustomizationsResponseSkinColor.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseSkinColor.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseHair.isNullOrEmpty()){
                _resultForRecyclerViewHair.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseHair.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseHair.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseHairColor.isNullOrEmpty()){
                _resultForRecyclerViewHairColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseHairColor.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseHairColor.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseEyeColor.isNullOrEmpty()){
                _resultForRecyclerViewEyeColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseEyeColor.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseEyeColor.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseEyeBrows.isNullOrEmpty()){
                _resultForRecyclerViewEyebrows.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseEyeBrows.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseEyeBrows.first().options,
                    firstLoad = positionClicked == null)
                )
            }

        }

    }
    /////Pedir
//    1 - Shoes nao retornar cor...ou podera nao haver cor? --> perguntei
//    2 - imagem de place holder das imagens -->
//    3 - tela de erro de servico -->

    fun setTabClothes(avatar: Avatar, positionClicked: Int? = null){
        
        val avatarCustomizationsResponseGender = filterCustomizationsByKey(key = HeadFragment.GENDER, avatarCustomizationsResponse = avatar.headCustomizations, checkTags = false)

        val indexGender = avatarCustomizationsResponseGender.first().userOptionIdx
        val gender = avatarCustomizationsResponseGender.first().options[indexGender].criteria
        
        //tab clothes
        val avatarCustomizationsResponseTop = filterCustomizationsByKey(HeadFragment.TOP_CLOTHES, gender=gender, avatar.clothesCustomizations)
        val avatarCustomizationsResponseTopColor = filterCustomizationsByKey(HeadFragment.TOP_CLOTHES_COLOR, gender=gender, avatar.clothesCustomizations, checkTags = false)
        val avatarCustomizationsResponseBottoms = filterCustomizationsByKey(HeadFragment.BOTTOM_CLOTHES, gender=gender, avatar.clothesCustomizations)
        val avatarCustomizationsResponseBottomsColor = filterCustomizationsByKey(HeadFragment.BOTTOM_CLOTHES_COLOR, gender=gender, avatar.clothesCustomizations, checkTags = false)

        viewModelScope.launch(Dispatchers.IO){

            if(!avatarCustomizationsResponseTop.isNullOrEmpty()){
                _resultForRecyclerViewTop.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseTop.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseTop.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseTopColor.isNullOrEmpty()){
                _resultForRecyclerViewTopColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseTopColor.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseTopColor.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseBottoms.isNullOrEmpty()){
                _resultForRecyclerViewBottoms.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseBottoms.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseBottoms.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseBottomsColor.isNullOrEmpty()){
                _resultForRecyclerViewBottomsColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseBottomsColor.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseBottomsColor.first().options,
                    firstLoad = positionClicked == null)
                )
            }

        }
    }

    fun setTabShoes(avatar: Avatar, positionClicked: Int? = null){

        val avatarCustomizationsResponseGender = filterCustomizationsByKey(key = HeadFragment.GENDER, avatarCustomizationsResponse = avatar.headCustomizations, checkTags = false)

        val indexGender = avatarCustomizationsResponseGender.first().userOptionIdx
        val gender = avatarCustomizationsResponseGender.first().options[indexGender].criteria

        //tab shoes
        val avatarCustomizationsResponseShoes = filterCustomizationsByKey(HeadFragment.SHOES, gender=gender, avatar.shoesCustomizations)
        val avatarCustomizationsResponseShoesColor = filterCustomizationsByKey(HeadFragment.SHOES_COLOR, gender=gender, avatar.shoesCustomizations, checkTags = false)

        viewModelScope.launch(Dispatchers.IO){

            // Shoes
            if(!avatarCustomizationsResponseShoes .isNullOrEmpty()){
                _resultForRecyclerViewShoes.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseShoes.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseShoes.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseShoesColor .isNullOrEmpty()){
                _resultForRecyclerViewShoesColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseShoesColor.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseShoesColor.first().options,
                    firstLoad = positionClicked == null)
                )
            }

        }
    }

    fun setTabRides(avatar: Avatar, positionClicked: Int? = null){

        val avatarCustomizationsResponseGender = filterCustomizationsByKey(key = HeadFragment.GENDER, avatarCustomizationsResponse = avatar.headCustomizations, checkTags = false)

        val indexGender = avatarCustomizationsResponseGender.first().userOptionIdx
        val gender = avatarCustomizationsResponseGender.first().options[indexGender].criteria

        //tab rides
        val avatarCustomizationsResponseRides = filterCustomizationsByKey(HeadFragment.RIDES, gender=gender, avatar.ridesCustomizations)
        val avatarCustomizationsResponseRidesColor = filterCustomizationsByKey(HeadFragment.RIDES_COLOR, gender=gender, avatar.ridesCustomizations, checkTags = false)
        
        viewModelScope.launch(Dispatchers.IO){

            // Rides
            if(!avatarCustomizationsResponseRides.isNullOrEmpty()){
                _resultForRecyclerViewRides.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseRides.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseRides.first().options,
                    firstLoad = positionClicked == null)
                )
            }

            if(!avatarCustomizationsResponseRidesColor.isNullOrEmpty()){
                _resultForRecyclerViewRidesColor.postValue(CombinedResultForRecyclerView(
                    positionSelected = avatarCustomizationsResponseRidesColor.first().userOptionIdx,
                    listOptions = avatarCustomizationsResponseRidesColor.first().options,
                    firstLoad = positionClicked == null)
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

            val filteredOptions = options?.filter{ item -> gender in item.tags }

            return filteredOptions!!

        }catch (e: java.lang.Exception){
            return listOf()
        }

    }

}