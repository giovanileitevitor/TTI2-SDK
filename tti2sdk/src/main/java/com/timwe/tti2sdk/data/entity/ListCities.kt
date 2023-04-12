package com.timwe.tti2sdk.data.entity

import com.google.gson.Gson
import com.timwe.tti2sdk.data.model.response.*
import java.io.Serializable

data class ListCities(
    val listCities: List<City>,
): Serializable{

    fun clone(): ListCities{
        val listCities = Gson().toJson(this, ListCities::class.java)
        return Gson().fromJson(listCities, ListCities::class.java)
    }

}