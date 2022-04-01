package com.bbn.movitfriends.data.repository

import android.content.Context
import android.widget.ArrayAdapter
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.interfaces.ChatCallBack
import com.bbn.movitfriends.domain.interfaces.UserCallBack
import com.bbn.movitfriends.domain.model.Chat
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.ChatRepository
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val database: DatabaseReference,
    private val userRepository: UserRepository,
    private val loginUser: FirebaseUser?,
    private val context: Context
) : ChatRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getChats(chatCallBack: ChatCallBack) {
       database.child(Constants.CHAT_CHILD).orderByChild("time").addValueEventListener(object: ValueEventListener {
           override fun onDataChange(snapshot: DataSnapshot) {

               val chats = ArrayList<Chat>()
               val chatsAdapter = ArrayAdapter<Chat>(context, 0, chats)
               for(chat in snapshot.children){

                   val members = chat.child("members")
                   var isForMe = false
                   var otherUser = ""

                   for (member in members.children){
                       if (member.value.toString() == loginUser!!.uid)
                           isForMe = true
                       else
                           otherUser = member.value.toString()
                   }

                   if(isForMe){
                       userRepository.getUserById(otherUser, object : UserCallBack {
                           override fun onCallBack(user: User) {
                               chats.add(
                                   Chat(
                                       chatID = chat.key!!,
                                       userUid = otherUser,
                                       lastMessage = chat.child("lastMessage").value.toString(),
                                       name = user.fullName,
                                       imageUrl = user.imageUrl
                                   )
                               )

                               chatCallBack.onCallBack(chats)
                           }

                           override fun onFailure(error: String) {
                               TODO("Not yet implemented")
                           }
                       })
                   }
               }
           }

           override fun onCancelled(error: DatabaseError) {
               chatCallBack.onFailure(error.toException().localizedMessage ?: "An unexpected error occured!")
           }

       })
    }
}
