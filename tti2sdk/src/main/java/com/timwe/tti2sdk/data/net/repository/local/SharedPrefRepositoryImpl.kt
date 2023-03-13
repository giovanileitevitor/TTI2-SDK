package com.timwe.tti2sdk.data.net.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class SharedPrefRepositoryImpl(
    private val context: Context
) : SharedPrefRepository {

    companion object{
        val ID = stringPreferencesKey("ID")
        val HAS_AVATAR = booleanPreferencesKey("HAS_AVATAR")
    }

    override suspend fun getUserId(): String {
        return "empty"
    }

    override suspend fun saveUserId(userId: String) {

    }

    override suspend fun isUserHasAvatar(userId: String): Boolean{
        return false
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

}

val Context.datastore : DataStore<Preferences> by  preferencesDataStore(name = "JELAJAH-SDK")