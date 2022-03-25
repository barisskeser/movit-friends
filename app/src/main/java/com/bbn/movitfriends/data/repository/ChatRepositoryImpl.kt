package com.bbn.movitfriends.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.interfaces.UserCallBack
import com.bbn.movitfriends.domain.model.Chat
import com.bbn.movitfriends.domain.repository.ChatRepository
import com.bbn.movitfriends.domain.repository.MessageRepository
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository,
    private val messageRepository: MessageRepository,
    private val loginUser: FirebaseUser?
) : ChatRepository {

    private var _chats: MutableLiveData<ArrayList<Chat>> = MutableLiveData(ArrayList<Chat>())

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getChats(): MutableLiveData<ArrayList<Chat>> {
        firestore.collection(Constants.CHAT_COLLECTION).addSnapshotListener{ snapshot, error ->
            if(error != null){
                Log.d("ChatRepository", "getChats: " + error.localizedMessage ?: "An unexpected error occured")
                return@addSnapshotListener
            }
            val chats = ArrayList<Chat>()
            snapshot?.forEach { chat ->
                if(chat.getString("from").equals(loginUser?.uid) || chat.getString("to").equals(loginUser?.uid)){
                    val otherUserId = if(chat.getString("from").equals(loginUser?.uid))
                        chat.getString("to")
                    else
                        chat.getString("from")
                    callbackFlow<UserRepository> {
                        val chat = Chat(
                            userUid = otherUserId!!,
                            lastMessage = otherUserId.let { messageRepository.getLastMessageWithUser(it) }
                        )
                        chats.add(chat)
                    }
                }
            }
            _chats.value = chats
        }
        return _chats
    }
}