package com.bbn.movitfriends.presentation.friends

import android.accounts.NetworkErrorException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.NetworkHelper
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.use_case.friend.GetFriendsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsUseCase: GetFriendsUseCase,
    private val networkHelper: NetworkHelper
): ViewModel() {
    private val _state = mutableStateOf(FriendsState())
    val state: State<FriendsState> = _state

    init {
        if (networkHelper.isNetworkConnected())
            getFriends()
        else
            _state.value = FriendsState(error = NetworkErrorException().localizedMessage)
    }

    private fun getFriends(){
        friendsUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = FriendsState(friendList = result.data)
                }
                is Resource.Loading -> {
                    _state.value = FriendsState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = FriendsState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}