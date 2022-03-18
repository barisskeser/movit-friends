package com.bbn.movitfriends.presentation.filter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.repository.FilterDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val dataStore: FilterDataStoreManager
) : ViewModel() {

    private val _state = mutableStateOf(FilterState())
    val state: State<FilterState> = _state

    init {
        getPreferences()
    }

    private fun getPreferences() = viewModelScope.launch {
        _state.value = FilterState(
            beginAge = dataStore.readInt(Constants.FILTER_FROM_AGE_KEY) ?: 20,
            endAge = dataStore.readInt(Constants.FILTER_TO_AGE_KEY) ?: 30,
            gender = dataStore.readString(Constants.FILTER_GENDER_KEY) ?: "Both",
            distance = dataStore.readInt(Constants.FILTER_DISTANCE_KEY) ?: 50,
            status = dataStore.readString(Constants.FILTER_STATUS_KEY) ?: "Any"
        )
    }

    fun setStringPreferences(key: String, value: String) = viewModelScope.launch {
        dataStore.saveString(key, value)
        getPreferences()
    }

    fun setIntPreferences(key: String, value: String) = viewModelScope.launch {
        dataStore.saveString(key, value)
        getPreferences()
    }
}