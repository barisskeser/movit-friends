package com.bbn.movitfriends.domain.interfaces

import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.bbn.movitfriends.domain.model.Chat
import java.lang.Exception

interface ChatCallBack {

    fun onCallBack(chats: List<Chat>)

    fun onFailure(error: String)
}