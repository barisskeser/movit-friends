package com.bbn.movitfriends.domain.repository

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.model.User
import com.google.firebase.auth.FirebaseUser
import java.util.ArrayList
import javax.inject.Inject

interface FriendRepository{

    suspend fun getFriends(): MutableLiveData<ArrayList<User>>

    suspend fun isMyFriend(uid: String): Boolean
}