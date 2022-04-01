package com.bbn.movitfriends.presentation.chat

sealed class ChatEvent {

    data class OnChatClicked(val chatID: String, val userUid: String): ChatEvent()

    object OFriendsClicked: ChatEvent()

}
