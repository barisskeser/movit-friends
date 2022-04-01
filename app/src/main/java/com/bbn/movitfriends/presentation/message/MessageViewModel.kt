package com.bbn.movitfriends.presentation.message

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.common.NetworkHelper
import com.bbn.movitfriends.common.UiEvent
import com.bbn.movitfriends.domain.interfaces.MessageCallBack
import com.bbn.movitfriends.domain.interfaces.UserCallBack
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.MessageRepository
import com.bbn.movitfriends.domain.repository.UserRepository
import com.bbn.movitfriends.presentation.Screen
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    networkHelper: NetworkHelper,
    private val loginUser: FirebaseUser?,
    savedStateHandle: SavedStateHandle
) : ViewModel(), MessageCallBack, UserCallBack {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _messagesLiveData = MutableLiveData<List<Message>>(emptyList())
    val messageLiveData: LiveData<List<Message>> = _messagesLiveData

    private val _chatID = mutableStateOf("")
    val chatID: String = _chatID.value

    private val _otherUser = mutableStateOf(User())
    val otherUser: User = _otherUser.value

    init {
        Log.d("MessageViewModel", "init: ")
        if (networkHelper.isNetworkConnected()) {
            savedStateHandle.get<String>(Constants.PARAM_CHAT_ID)?.let {
                _chatID.value = it
                getMessages(it)
            }
            savedStateHandle.get<String>(Constants.PARAM_USER_ID)?.let {
                Log.d("MessageViewModel", "userid: $it")
                getOtherUser(it)
            }
        } else {
            sendUiEvent(UiEvent.ShowError("Device is not connected to network"))
        }
    }

    fun onEvent(event: MessageEvent){
        when (event) {
            is MessageEvent.OnSendMessage -> {
                sendMessage(event.chatID, event.message)
            }

            is MessageEvent.OnClickUserProfile -> {
                sendUiEvent(UiEvent.Navigate(Screen.ProfileScreen.route + "/${otherUser.id}"))
            }

            is MessageEvent.OnBack -> {
                sendUiEvent(UiEvent.NavigatePop)
            }
        }
    }

    private fun getMessages(chatID: String)  = viewModelScope.launch{
        messageRepository.getMessages(chatID, this@MessageViewModel)
    }

    private fun getOtherUser(uid: String) = viewModelScope.launch {
        userRepository.getUserById(uid, this@MessageViewModel)
    }

    private fun sendMessage(chatID: String, message: String) = viewModelScope.launch {
        val messageObject = Message(
            from = loginUser!!.uid,
            to = otherUser.id,
            text = message
        )
        messageRepository.sendMessage(chatID, messageObject)
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    override fun onCallBack(messages: List<Message>) {("MessageViewModel changed")
        _messagesLiveData.postValue(messages)
    }

    override fun onCallBack(user: User) {
        _otherUser.value = user
    }

    override fun onFailure(error: String) {
        sendUiEvent(UiEvent.ShowError(error))
    }
}