package com.bbn.movitfriends.data.repository

import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.model.toHashMap
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    private val TAG: String = "UserRepository"
    private var user: DocumentSnapshot? = null

    override suspend fun getUserById(uid: String): User {
        firestore.collection(Constants.USER_COLLECTION).document(uid).get().addOnSuccessListener {
            user = it
        }.addOnFailureListener{ exception ->
            throw exception
        }
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
            age = user?.get("age") as Int,
            createDate = user?.getTimestamp("time")
        )
    }

    override suspend fun updateUserById(user: User) {
        firestore.collection(Constants.USER_COLLECTION).document(user.id!!).update(user.toHashMap()).addOnFailureListener { exception ->
            throw exception
        }
    }

    override suspend fun signInUser(user: User) {
        firestore.collection(Constants.USER_COLLECTION).add(user.toHashMap()).addOnFailureListener { exception ->
            throw exception
        }
    }
}