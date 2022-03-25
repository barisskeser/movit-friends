package com.bbn.movitfriends.domain.interfaces

import com.bbn.movitfriends.domain.model.Chat
import java.lang.Exception

interface ChatCallBack {

    fun onCallBack(chat: Chat)

    fun onFailure(e: Exception)

}