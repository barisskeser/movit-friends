package com.bbn.movitfriends.presentation.requests

import android.accounts.NetworkErrorException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.NetworkHelper
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.use_case.request.GetRequestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val getRequestsUseCase: GetRequestsUseCase,
    private val networkHelper: NetworkHelper
): ViewModel() {
    private val _state = mutableStateOf(RequestState())
    val state: State<RequestState> = _state

    init {
        if (networkHelper.isNetworkConnected())
            getRequests()
        else
            _state.value = RequestState(error = NetworkErrorException().localizedMessage)
    }

    private fun getRequests() {
        getRequestsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RequestState(requestList = result.data)
                }
                is Resource.Loading -> {
                    _state.value = RequestState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = RequestState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}