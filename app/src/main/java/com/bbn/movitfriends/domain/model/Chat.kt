package com.bbn.movitfriends.domain.model

import androidx.lifecycle.MutableLiveData

data class Chat(
    val userUid: String,
    val lastMessage: MutableLiveData<Message>?
)