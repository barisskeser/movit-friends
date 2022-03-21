package com.bbn.movitfriends.presentation.profile

import android.accounts.NetworkErrorException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.common.NetworkHelper
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.use_case.profile.GetUserUseCase
import com.bbn.movitfriends.domain.use_case.profile.IsMeUseCase
import com.bbn.movitfriends.domain.use_case.profile.UpdateProfileUseCase
import com.bbn.movitfriends.domain.use_case.request.GetRequestStateUseCase
import com.bbn.movitfriends.domain.use_case.request.SendRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val isMeUseCase: IsMeUseCase,
    private val sendRequestUseCase: SendRequestUseCase,
    private val getRequestStateUseCase: GetRequestStateUseCase,
    private val networkHelper: NetworkHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_USER_ID)?.let {
            if(networkHelper.isNetworkConnected()) {
                getUser(it)
                getRequestState(it)
            }
            else
                _state.value = ProfileState(error = NetworkErrorException().localizedMessage)
        }
    }

    private fun getUser(userUid: String) {
        getUserUseCase(userUid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProfileState(user = result.data, isMe = isMeUseCase(userUid))
                }
                is Resource.Loading -> {
                    _state.value = ProfileState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ProfileState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRequestState(uid: String){
        val stateUser = _state.value.user
        val isMe = _state.value.isMe

        getRequestStateUseCase(uid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProfileState(user = stateUser, isMe = isMe, requestState = result.data?: "")
                }
                is Resource.Error -> {
                    _state.value = ProfileState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateProfile(user: User) = viewModelScope.launch{
        val stateUser = _state.value.user
        val isMe = _state.value.isMe
        val requestState = _state.value.requestState
        updateProfileUseCase(user).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = ProfileState(user = stateUser, isMe = isMe, requestState = requestState)
                }
                is Result.Error -> {
                    _state.value = ProfileState(error = result.message, user = stateUser, isMe = isMe, requestState = requestState)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sendRequest(uid: String) {
        val stateUser = _state.value.user
        val isMe = _state.value.isMe
        val requestState = _state.value.requestState
        sendRequestUseCase(uid).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = ProfileState(user = stateUser, isMe = isMe, requestState = requestState)
                }
                is Result.Error -> {
                    _state.value = ProfileState(error = result.message, user = stateUser, isMe = isMe, requestState = requestState)
                }
            }
        }.launchIn(viewModelScope)
    }

}