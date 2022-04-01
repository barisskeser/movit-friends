package com.bbn.movitfriends.presentation.profile

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.*
import com.bbn.movitfriends.domain.interfaces.UserCallBack
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.UserRepository
import com.bbn.movitfriends.domain.use_case.request.GetRequestStateUseCase
import com.bbn.movitfriends.domain.use_case.request.SendRequestUseCase
import com.bbn.movitfriends.presentation.Screen
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sendRequestUseCase: SendRequestUseCase,
    private val getRequestStateUseCase: GetRequestStateUseCase,
    private val userRepository: UserRepository,
    private val networkHelper: NetworkHelper,
    private val loginUser: FirebaseUser?,
    savedStateHandle: SavedStateHandle
) : ViewModel(), UserCallBack {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {

        Log.d("ProfileViewModel", "Initialized")

        savedStateHandle.get<String>(Constants.PARAM_USER_ID)?.let {
            if (networkHelper.isNetworkConnected()) {
                getUser(it)
            } else
                sendUiEvent(UiEvent.ShowError(NetworkErrorException().localizedMessage ?: "Connection error!"))
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnActionClicked -> {
                if (_state.value.request == "accepted") // It' my friend
                    sendUiEvent(UiEvent.Navigate(Screen.MessageScreen.route + "/${event.userUid}"))
                else
                    sendRequest(event.userUid)
            }

            is ProfileEvent.OnClickEdit -> {
                sendUiEvent(UiEvent.Navigate(Screen.SettingsScreen.route))
            }

            is ProfileEvent.OnBackIconClicked -> {
                sendUiEvent(UiEvent.NavigatePop)
            }
        }
    }

    private fun getUser(userUid: String) = viewModelScope.launch {
        Log.d("ProfileViewModel", "getUser")
        sendUiEvent(UiEvent.ShowLoading)
        userRepository.getUserById(userUid, this@ProfileViewModel)
    }

    private fun getRequestStateWithMe(uid: String) {
        val stateUser = _state.value.user

        getRequestStateUseCase(uid).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProfileState(user = stateUser, isMe = isMe(uid), request = result.data ?: "")
                }
                is Resource.Error -> {
                    sendUiEvent(UiEvent.ShowError(result.message ?: "Something went wrong! Please, try Again!"))
                }
                is Resource.Loading -> {
                    //_state.value = ProfileState(user = stateUser, isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    /*fun updateProfile(user: User) = viewModelScope.launch {
        val stateUser = _state.value.user
        val isMe = _state.value.isMe
        val requestState = _state.value.requestState
        updateProfileUseCase(user).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value =
                        ProfileState(user = stateUser, isMe = isMe, requestState = requestState)
                }
                is Result.Error -> {
                    _state.value = ProfileState(
                        error = result.message,
                        user = stateUser,
                        isMe = isMe,
                        requestState = requestState
                    )
                }
            }
        }.launchIn(viewModelScope)
    }*/

    private fun sendRequest(uid: String) {
        val stateUser = _state.value.user
        val isMe = _state.value.isMe
        val request = _state.value.request
        sendRequestUseCase(uid).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value =
                        ProfileState(user = stateUser, isMe = isMe, request = request)
                }
                is Result.Error -> {
                    sendUiEvent(UiEvent.ShowError(result.message ?: "Something went wrong! Please, try Again!"))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun isMe(userUid: String): Boolean {
        return loginUser?.uid == userUid
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    override fun onFailure(error: String) {
        sendUiEvent(UiEvent.ShowError(error))
    }

    override fun onCallBack(user: User) {
        sendUiEvent(UiEvent.TerminateLoading)
        _state.value = ProfileState(user = user, isMe = isMe(user.id))
        getRequestStateWithMe(user.id)
    }

}