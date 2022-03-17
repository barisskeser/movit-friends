package com.bbn.movitfriends.domain.repository

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.model.Message

interface MessageRepository {

    suspend fun getMessages(friendUid: String): MutableLiveData<ArrayList<Message>>

    suspend fun getLastMessageWithUser(userUid: String): MutableLiveData<Message>

    suspend fun sendMessage(message: Message)
}