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
        val CHECKUP_TERMS_ACCEPTED = booleanPreferencesKey("CHECKUP_TERMS_ACCEPTED")
        val FIRST_ACCESS_AVATAR = booleanPreferencesKey("FIRST_ACESS_AVATAR")
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

}

val Context.datastore : DataStore<Preferences> by  preferencesDataStore(name = "JELAJAH-SDK")