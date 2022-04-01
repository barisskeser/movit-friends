package com.bbn.movitfriends.data.repository

import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.interfaces.MessageCallBack
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.repository.MessageRepository
import com.google.firebase.Timestamp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MessageRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
) : MessageRepository {


    override suspend fun getMessages(chatID: String, messageCallBack: MessageCallBack) {
        database.child(Constants.CHAT_CHILD).child(chatID).child(Constants.MESSAGE_CHILD).orderByChild("time").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageCallBack.onCallBack(
                    snapshot.children.map { it.getValue<Message>()!! }
                )
            }

            override fun onCancelled(error: DatabaseError) {
                messageCallBack.onFailure(error.toException().localizedMessage ?: "An unexpected error occured!")
            }

        })
    }

    override suspend fun sendMessage(chatID: String, message: Message) {
        // Create a chat if not exists and add messages
        database.child(Constants.CHAT_CHILD).child(chatID).child(Constants.MESSAGE_CHILD).child(message.id).setValue(message)
        database.child(Constants.CHAT_CHILD).child(chatID).child("members").setValue(listOf(message.from, message.to))
        database.child(Constants.CHAT_CHILD).child(chatID).child("lastMessage").setValue(message.text)
        database.child(Constants.CHAT_CHILD).child(chatID).child("from").setValue(message.from)
        database.child(Constants.CHAT_CHILD).child(chatID).child("time").setValue(message.time)
    }
}