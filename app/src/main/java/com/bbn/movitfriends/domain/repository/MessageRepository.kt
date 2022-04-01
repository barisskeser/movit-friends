package com.bbn.movitfriends.domain.repository

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.interfaces.MessageCallBack
import com.bbn.movitfriends.domain.model.Message

interface MessageRepository {

    suspend fun getMessages(chatID: String, messageCallBack: MessageCallBack)

    suspend fun sendMessage(chatID: String, message: Message)
}