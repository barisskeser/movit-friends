package com.bbn.movitfriends.presentation.filter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.domain.repository.FilterDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.logging.Filter
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
            beginAge = dataStore.readInt("beginAge") ?: 20,
            endAge = dataStore.readInt("endAge") ?: 30,
            gender = dataStore.readString("gender") ?: "Both",
            distance = dataStore.readInt("distance") ?: 50,
            status = dataStore.readString("status") ?: "Any"
        )
    }

    fun setStringPreferences(map: Map<String, String>) = viewModelScope.launch {
        map.onEach {
            dataStore.saveString(it.key, it.value)
        }
    }

    fun setIntPreferences(map: Map<String, Int>) = viewModelScope.launch {
        map.onEach {
            dataStore.saveInt(it.key, it.value)
        }
    }
}