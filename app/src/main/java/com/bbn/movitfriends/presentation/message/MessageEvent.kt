package com.bbn.movitfriends.presentation.message

sealed class MessageEvent {

    data class OnSendMessage(val chatID: String, val message: String): MessageEvent()

    object OnClickUserProfile: MessageEvent()

    object OnBack: MessageEvent()

}
