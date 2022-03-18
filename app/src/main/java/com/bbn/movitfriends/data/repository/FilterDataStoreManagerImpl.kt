package com.bbn.movitfriends.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import com.bbn.movitfriends.domain.repository.FilterDataStoreManager
import androidx.datastore.preferences.createDataStore
import com.bbn.movitfriends.common.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterDataStoreManagerImpl @Inject constructor(
    private val context: Context
): FilterDataStoreManager {

    private val dataStore: DataStore<Preferences> = context.createDataStore(Constants.FILTER_PREFERENCES)

    override suspend fun saveString(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    override suspend fun saveInt(key: String, value: Int){
        val dataStoreKey = preferencesKey<Int>(key)
        dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    override suspend fun readString(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        return dataStore.data.first()[dataStoreKey]
    }

    override suspend fun readInt(key: String): Int? {
        val dataStoreKey = preferencesKey<Int>(key)
        return dataStore.data.first()[dataStoreKey]
    }

}