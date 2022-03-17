package com.bbn.movitfriends.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.model.toHashMap
import com.bbn.movitfriends.domain.repository.MessageRepository
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val loginUser: FirebaseUser?,
    private val userRepository: UserRepository
): MessageRepository {

    private var _messageList: MutableLiveData<ArrayList<Message>> = MutableLiveData<ArrayList<Message>>()
    private var _message: MutableLiveData<Message> = MutableLiveData<Message>()
    private val TAG: String = "MessageRepository"

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getMessages(friendUid: String): MutableLiveData<ArrayList<Message>> {
        firestore.collection(Constants.MESSAGE_COLLECTION)
            .whereIn("to", listOf(friendUid, loginUser?.uid))
            .whereIn("from", listOf(friendUid, loginUser?.uid))
            .addSnapshotListener{ snapshot, error ->

                if(error != null){
                    Log.d("MessageRepository", "getMessages: " + error.localizedMessage)
                    return@addSnapshotListener
                }

                val messageList = ArrayList<Message>()
                snapshot?.forEach { message ->
                    callbackFlow<UserRepository> {
                        val userMessage = Message(
                            id = message.id,
                            from = message.getString("from")?.let { userRepository.getUserById(it) },
                            to = message.getString("to")?.let { userRepository.getUserById(it) },
                            text = message.getString("message"),
                            time = message.getTimestamp("time"),
                            isSeen = message.getBoolean("isSeen")
                        )

                        messageList.add(userMessage)
                    }
                }

                _messageList.value = messageList

            }

        return _messageList
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getLastMessageWithUser(userUid: String): MutableLiveData<Message> {
        firestore.collection(Constants.MESSAGE_COLLECTION)
            .whereIn("to", listOf(userUid, loginUser?.uid))
            .whereIn("from", listOf(userUid, loginUser?.uid))
            .addSnapshotListener{ snapshot, error ->

                if(error != null){
                    Log.d("MessageRepository", "getLastMessageWithUser: " + error.localizedMessage)
                    return@addSnapshotListener
                }

                val lastMessage = snapshot?.last()
                var message: Message? =  null
                callbackFlow<UserRepository> {
                    message = Message(
                        id = lastMessage?.id,
                        from = lastMessage?.getString("from")?.let { userRepository.getUserById(it) },
                        to = lastMessage?.getString("to")?.let { userRepository.getUserById(it) },
                        text = lastMessage?.getString("message"),
                        time = lastMessage?.getTimestamp("time"),
                        isSeen = lastMessage?.getBoolean("isSeen")
                    )
                }
                _message.value = message
            }

        return _message
    }

    override suspend fun sendMessage(message: Message) {
        firestore.collection(Constants.MESSAGE_COLLECTION).add(message.toHashMap()).addOnSuccessListener {
            Log.d(TAG, "insertMessage: Success")
        }.addOnFailureListener {
            Log.d(TAG, "insertMessage: Failure")
        }.addOnCanceledListener {
            Log.d(TAG, "insertMessage: Canceled")
        }
    }
}