package com.bbn.movitfriends.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.model.toHashMap
import com.bbn.movitfriends.domain.repository.FriendRepository
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import java.util.ArrayList
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val loginUser: FirebaseUser?,
    private val userRepository: UserRepository
): FriendRepository {

    private val _friendList: MutableLiveData<ArrayList<User>> = MutableLiveData<ArrayList<User>>()
    private val uid = loginUser?.uid

    /**
     * uid: Login User
     * if request accepted then it is a friendship
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getFriends(): MutableLiveData<ArrayList<User>> {
        firestore.collection(Constants.REQUEST_COLLECTION)
            .whereEqualTo("status", "accepted")
            .addSnapshotListener{ snapshot, error ->

                if(error != null){
                    Log.d("UserRepository", "getFriends: " + error.localizedMessage)
                    return@addSnapshotListener
                }

                val friendList = ArrayList<User>()
                snapshot?.forEach { request ->
                    if (request.getString("from").equals(uid) || request.getString("to").equals(uid)) {
                        callbackFlow<UserRepository> {
                            val user: User? = if (request.getString("from").equals(uid))
                                request.getString("to")?.let { friendToUser(it) }
                            else
                                request.getString("from")?.let { friendToUser(it) }

                            user?.let { friendList.add(it) }
                        }
                    }
                }
                _friendList.value = friendList
            }

        return _friendList
    }

    private suspend fun friendToUser(uid: String): User?{
        return userRepository.getUserById(uid)
    }
}