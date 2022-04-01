package com.bbn.movitfriends.presentation.chat

import android.accounts.NetworkErrorException
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.*
import com.bbn.movitfriends.common.NetworkHelper
import com.bbn.movitfriends.common.UiEvent
import com.bbn.movitfriends.domain.interfaces.ChatCallBack
import com.bbn.movitfriends.domain.model.Chat
import com.bbn.movitfriends.domain.repository.ChatRepository
import com.bbn.movitfriends.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val networkHelper: NetworkHelper,
    private val context: Context
) : ViewModel(), ChatCallBack {

    private val _chatsLiveData = MutableLiveData<List<Chat>>()
    val chatsLiveData: LiveData<List<Chat>> = _chatsLiveData

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val chatsAdapter = ArrayAdapter<Chat>(context, 0, )


    init {
        Log.d("ChatViewModel", "Opened")
        if (networkHelper.isNetworkConnected())
            getChats()
        else
            sendUiEvent(
                UiEvent.ShowError(
                    NetworkErrorException().localizedMessage ?: "Connection Error!"
                )
            )
    }

    fun onEvent(event: ChatEvent) {

        when (event) {
            is ChatEvent.OnChatClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.MessageScreen.route + "/${event.chatID}/${event.userUid}"))
            }

            is ChatEvent.OFriendsClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.FriendScreen.route))
            }
        }

    }

    private fun getChats() = viewModelScope.launch {
        Log.d("ChatViewModel", "getChats")
        sendUiEvent(UiEvent.ShowLoading)
        chatRepository.getChats(this@ChatViewModel)
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    override fun onCallBack(chats: List<Chat>) {
        sendUiEvent(UiEvent.TerminateLoading)
        Log.d("ChatViewModel", "onCallBack: $chats")
        _chatsLiveData.postValue(chats)
    }


    override fun onFailure(error: String) {
        sendUiEvent(UiEvent.ShowError(error))
    }
}