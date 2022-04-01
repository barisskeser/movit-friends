package com.bbn.movitfriends.domain.model

import androidx.lifecycle.LiveData

data class Chat(
    val chatID: String,
    val userUid: String,
    val name: String,
    val imageUrl: String,
    val lastMessage: String
)