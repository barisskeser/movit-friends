package com.bbn.movitfriends.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.model.toHashMap
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.collections.ArrayList

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    private val TAG: String = "UserRepository"

    override suspend fun getUserById(uid: String): User {
        val user = firestore.collection(Constants.USER_COLLECTION).document(uid).get().result
        return User(
            username = user?.getString("username"),
            fullName = user?.getString("fullName"),
            gender = user?.getString("gender"),
            email = user?.getString("email"),
            lat = user?.getDouble("lat"),
            lng = user?.getDouble("lng"),
            isAdBlocked = user?.getBoolean("isAdBlocked"),
            isPremium = user?.getBoolean("isPremium"),
            imageUrl = user?.getString("imageUrl"),
            status = user?.getString("status"),
            id = user?.getString("id"),
            credit = user?.get("credit") as Int,
            age = user.get("age") as Int,
            createDate = user.getTimestamp("time")
        )
    }

    override suspend fun updateUserById(user: User) {
        firestore.collection(Constants.USER_COLLECTION).document(user.id!!).update(user.toHashMap()).addOnSuccessListener {
            Log.d(TAG, "updateUserById: Success")
        }.addOnFailureListener {
            Log.d(TAG, "updateUserById: Failure")
        }.addOnCanceledListener {
            Log.d(TAG, "updateUserById: Canceled")
        }
    }
}