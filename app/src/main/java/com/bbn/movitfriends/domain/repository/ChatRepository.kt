package com.bbn.movitfriends.domain.repository

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.interfaces.ChatCallBack
import com.bbn.movitfriends.domain.model.Chat
import com.google.firebase.firestore.auth.User

interface ChatRepository {

    suspend fun getChats(chatCallBack: ChatCallBack)

}