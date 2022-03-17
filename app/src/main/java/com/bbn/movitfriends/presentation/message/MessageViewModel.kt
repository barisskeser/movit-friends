package com.bbn.movitfriends.presentation.message

import android.accounts.NetworkErrorException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.common.NetworkHelper
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.use_case.message.SendMessageUseCase
import com.bbn.movitfriends.domain.use_case.message.GetMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageUseCase: GetMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val networkHelper: NetworkHelper,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = mutableStateOf(MessageState())
    val state: State<MessageState> = _state

    private val TAG: String = this::class.java.simpleName

    init {
        if (networkHelper.isNetworkConnected())
            //getMessages(it)
        else {
            _state.value = MessageState(error = "Device is not connected to network")
            println("İs")
        }
        savedStateHandle.get<String>(Constants.PARAM_USER_ID)?.let {

        }
    }

    private fun getMessages(uid: String){
        messageUseCase(uid).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = MessageState(messageList = result.data)
                }
                is Resource.Loading -> {
                    _state.value = MessageState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MessageState(error = result.message ?: "An unexpected error occured")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun sendMessage(message: Message) = viewModelScope.launch {
        sendMessageUseCase(message)
    }
}