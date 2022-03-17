package com.bbn.movitfriends.presentation.chat

import android.accounts.NetworkErrorException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.NetworkHelper
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.use_case.chat.GetChatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatsUseCase: GetChatsUseCase,
    private val networkHelper: NetworkHelper
): ViewModel() {
    private val _state = mutableStateOf(ChatState())
    val state: State<ChatState> = _state

    init {
        if(networkHelper.isNetworkConnected())
            getChats()
        else
            _state.value = ChatState(error = NetworkErrorException().localizedMessage)
    }

    private fun getChats(){
        chatsUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = ChatState(chatList = result.data)
                }
                is Resource.Loading -> {
                    _state.value = ChatState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ChatState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}