package com.timwe.tti2sdk.data.net.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.ListCities
import com.timwe.tti2sdk.data.entity.UrlAddress
import kotlinx.coroutines.flow.first

class SharedPrefDataSourceImpl(
    private val context: Context
) : SharedPrefDataSource {

    companion object{
        val ID = stringPreferencesKey("ID")
        val HAS_AVATAR = booleanPreferencesKey("HAS_AVATAR")
        val CHECKUP_TERMS_ACCEPTED = booleanPreferencesKey("CHECKUP_TERMS_ACCEPTED")
        val FIRST_ACCESS_AVATAR = booleanPreferencesKey("FIRST_ACESS_AVATAR")
        val LIST_URLS = stringPreferencesKey("LIST_URLS")
        val LIST_CITIES = stringPreferencesKey("LIST_CITIES")
    }

    override suspend fun saveCheckupTerms(termsAccepted: Boolean) {
        context.datastore.edit { preferences ->
            preferences[CHECKUP_TERMS_ACCEPTED] = termsAccepted
        }
    }

    override suspend fun getCheckupTermsStatus(): Boolean {
        val preferences = context.datastore.data.first()
        return preferences[CHECKUP_TERMS_ACCEPTED] ?: false
    }

    override suspend fun getUserId(): String {
        val preferences = context.datastore.data.first()
        return preferences[ID] ?: ""
    }

    override suspend fun saveUserId(userId: String) {
        context.datastore.edit { preferences ->
            preferences[ID] = userId
        }
    }

    override suspend fun isUserHasAvatar(userId: String): Boolean{
        val preferences = context.datastore.data.first()
        return preferences[HAS_AVATAR] ?: false
    }

    override suspend fun isFistAccessAvatar(): Boolean {
        val preferences = context.datastore.data.first()
        return preferences[FIRST_ACCESS_AVATAR] ?: true
    }

    override suspend fun saveFistAccessAvatar(firstAccesAvatar: Boolean) {
        context.datastore.edit { preferences ->
            preferences[FIRST_ACCESS_AVATAR] = firstAccesAvatar
        }
    }

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.datastore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.datastore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun saveUrls(urlAddress: UrlAddress) {
        val urlAddress = Gson().toJson(urlAddress, UrlAddress::class.java)
        context.datastore.edit { preferences ->
            preferences[LIST_URLS] = urlAddress
        }
    }

    override suspend fun getUrls(): UrlAddress {
        val preferences = context.datastore.data.first()
        val urlAddressString = preferences[LIST_URLS]
        return Gson().fromJson(urlAddressString,  UrlAddress::class.java)
    }

    override suspend fun saveListCities(listCities: ListCities) {
        val urlAddress = Gson().toJson(listCities, ListCities::class.java)
        context.datastore.edit { preferences ->
            preferences[LIST_CITIES] = urlAddress
        }
    }

    override suspend fun getListCities(): ListCities {
        val preferences = context.datastore.data.first()
        val listCities = preferences[LIST_URLS]
        return Gson().fromJson(listCities,  ListCities::class.java)
    }

}

val Context.datastore : DataStore<Preferences> by  preferencesDataStore(name = "JELAJAH-SDK")