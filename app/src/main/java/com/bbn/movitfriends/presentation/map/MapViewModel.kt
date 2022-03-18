package com.bbn.movitfriends.presentation.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.NetworkHelper
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.use_case.profile.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val getUsersUseCase: GetUsersUseCase
): ViewModel() {

    private val _state = mutableStateOf(MapState())
    val state: State<MapState> = _state

    init {
        if(networkHelper.isNetworkConnected())
            getUsers()
        else
            _state.value = MapState(error = "Device is not connected to network")
    }

    private fun getUsers(){
        getUsersUseCase().onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = MapState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = MapState(users = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = MapState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

}