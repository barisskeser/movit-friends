package com.bbn.movitfriends.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.RequestRepository
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import java.util.ArrayList
import javax.inject.Inject

class RequestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val loginUser: FirebaseUser?,
    private val userRepository: UserRepository
): RequestRepository {

    private var _requestList: MutableLiveData<ArrayList<User>> = MutableLiveData<ArrayList<User>>()
    private val uid = loginUser?.uid

    private var exception: Exception? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getRequests(): MutableLiveData<ArrayList<User>> {

        firestore.collection(Constants.REQUEST_COLLECTION)
            .whereNotEqualTo("status", "accepted")
            .addSnapshotListener{ snapshot, error ->

                if(error != null){
                    Log.d("UserRepository", "getRequests: " + error.localizedMessage)
                    return@addSnapshotListener
                }

                val requestList = ArrayList<User>()
                snapshot?.forEach { request ->
                    if (request.getString("to").equals(uid)) {
                        callbackFlow<UserRepository> {
                            val user = request.getString("from")?.let { requestToUser(it) }
                            user?.let { requestList.add(it) }
                        }
                    }
                }
                _requestList.value = requestList
            }

        return _requestList
    }

    override suspend fun sendRequest(uid: String) {
        val map = HashMap<String, Any>()
        map.put("from", loginUser!!.uid)
        map.put("to", uid)
        map.put("status", "sent")
        map.put("time", FieldValue.serverTimestamp())

        firestore.collection(Constants.REQUEST_COLLECTION).add(map).addOnFailureListener {
            exception = it
        }

        exception?.let {

        }
    }

    override suspend fun getRequestState(uid: String): String? {

        val result = firestore.collection(Constants.REQUEST_COLLECTION)
            .whereEqualTo("status", "accepted")
            .whereArrayContains("from", listOf(uid, loginUser?.uid))
            .whereArrayContains("to", listOf(uid, loginUser?.uid))
            .get()
            .result?.first()

        return result?.getString("status")
    }

    private suspend fun requestToUser(uid: String): User?{
        return userRepository.getUserById(uid)
    }
}