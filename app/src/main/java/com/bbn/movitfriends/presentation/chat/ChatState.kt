package com.bbn.movitfriends.presentation.chat

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.model.Chat

data class ChatState(
    val isLoading: Boolean = false,
    val chatList: MutableLiveData<ArrayList<Chat>>? = MutableLiveData<ArrayList<Chat>>(),
    val error: String? = ""
)