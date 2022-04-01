package com.bbn.movitfriends.domain.interfaces

import com.bbn.movitfriends.domain.model.Message

interface MessageCallBack {

    fun onCallBack(messages: List<Message>)

    fun onFailure(error: String)

}