package com.timwe.tti2sdk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URL

class HomeViewModel(
    private val localRepository: SharedPrefDataSource
): ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _avatarName = MutableLiveData<String>()
    val avatarName: LiveData<String> get() = _avatarName

    private val _mapStructure = MutableLiveData<ByteArray>()
    val mapStructure: LiveData<ByteArray> get() = _mapStructure

    private val _itemClicked = MutableLiveData<Int>()
    val itemClicked: LiveData<Int> get() = _itemClicked

    fun saveData(data: String){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun startLoading(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            delay(5000)
            _loading.postValue(false)
        }
    }

    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            _avatarName.postValue("Avatar name AAA ")
        }
    }

    fun getMap(){
        viewModelScope.launch(Dispatchers.IO) {
            val mapRiveUrl = "https://webportals.cachefly.net/indonesia/telkomsel/tti/v2/riv/map.riv"
            _mapStructure.postValue(
                URL(mapRiveUrl).openStream().use {
                    it.readBytes()
                }
            )
        }
    }

    fun findItemClicked(xValue: Float, yValue: Float){
        _itemClicked.postValue(
            findIndex(
                positions = mapPosition(),
                xValue = xValue,
                yValue = yValue
            )
        )
    }

    private fun findIndex(positions: ArrayList<HomeActivity.Position>, xValue: Float, yValue: Float): Int{
        val tolerancia = 20f
        val valuesX = arrayListOf<Float>()
        val valuesY = arrayListOf<Float>()
        positions.forEach {
            valuesX.add(it.coordenateX)
            valuesY.add(it.coordenateY)
        }

        //Busca a posicao na lista X
        val valoresProximosEmX = valuesX.filterIndexed { index, valor -> kotlin.math.abs(valor - xValue ) <= tolerancia  }
        val posicoesValoresProximosX = valuesX.mapIndexedNotNull{ index, valor -> if(valoresProximosEmX.contains(valor)) index + 1 else null}

        val valoresProximosEmY = valuesY.filterIndexed { index, valor -> kotlin.math.abs(valor - yValue ) <= tolerancia  }
        val posicoesValoresProximosY = valuesY.mapIndexedNotNull{ index, valor -> if(valoresProximosEmY.contains(valor)) index + 1 else null}

        val a = 10

        return 1
    }

    private fun mapPosition() : ArrayList<HomeActivity.Position>{
        val positions = arrayListOf<HomeActivity.Position>()
        positions.add(HomeActivity.Position(1, 283.5f, 892.5f))
        positions.add(HomeActivity.Position(2, 600.0f, 1005.5f))
        positions.add(HomeActivity.Position(3, 799.0f, 1108.0f))
        positions.add(HomeActivity.Position(4, 952.0f, 1192.0f))
        positions.add(HomeActivity.Position(5, 1434.0f, 1339.0f))
        positions.add(HomeActivity.Position(6, 1599.0f, 1428.0f))
        positions.add(HomeActivity.Position(7, 2047.0f, 1416.0f))
        positions.add(HomeActivity.Position(8, 2577.0f, 1390.0f))
        positions.add(HomeActivity.Position(9, 2727.0f, 1367.0f))
        positions.add(HomeActivity.Position(10, 2877.0f, 1353.0f))
        positions.add(HomeActivity.Position(11, 2995.0f, 1347.0f))
        positions.add(HomeActivity.Position(12, 3294.0f, 1422.0f))
        positions.add(HomeActivity.Position(13, 3447.0f, 1431.0f))
        positions.add(HomeActivity.Position(14, 3254.0f, 1281.0f))
        positions.add(HomeActivity.Position(15, 3481.0f, 1264.0f))
        positions.add(HomeActivity.Position(16, 2441.0f, 1039.0f))
        positions.add(HomeActivity.Position(17, 2115.0f, 1054.0f))
        positions.add(HomeActivity.Position(18, 2556.0f, 904.0f))
        positions.add(HomeActivity.Position(19, 2588.0f, 722.0f))
        positions.add(HomeActivity.Position(20, 2712.0f, 754.0f))
        positions.add(HomeActivity.Position(21, 3031.0f, 789.0f))
        positions.add(HomeActivity.Position(22, 2987.0f, 944.0f))
        positions.add(HomeActivity.Position(23, 3267.0f, 1074.0f))
        positions.add(HomeActivity.Position(24, 3532.0f, 1045.0f))
        positions.add(HomeActivity.Position(25, 3152.0f, 705.0f))
        positions.add(HomeActivity.Position(26, 3459.0f, 673.0f))
        positions.add(HomeActivity.Position(27, 3545.0f, 553.0f))
        positions.add(HomeActivity.Position(28, 3845.0f, 826.0f))
        positions.add(HomeActivity.Position(29, 3776.0f, 604.0f))
        positions.add(HomeActivity.Position(30, 4276.0f, 786.0f))

        return positions
    }

}