package com.bbn.movitfriends.domain.model

import androidx.lifecycle.MutableLiveData

data class Chat(
    val user: User?,
    val lastMessage: MutableLiveData<Message>?
)