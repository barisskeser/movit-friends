package com.bbn.movitfriends.presentation.message

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.model.Message
import java.util.ArrayList

data class MessageState(
    val isLoading: Boolean = false,
    val messageList: MutableLiveData<ArrayList<Message>>? = MutableLiveData<ArrayList<Message>>(),
    val error: String = ""
)