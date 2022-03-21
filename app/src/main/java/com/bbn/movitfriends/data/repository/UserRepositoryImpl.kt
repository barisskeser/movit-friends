package com.bbn.movitfriends.data.repository

import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.model.toHashMap
import com.bbn.movitfriends.domain.repository.FilterDataStoreManager
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.type.DateTime
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val currentUser: FirebaseUser,
    private val dataStore: FilterDataStoreManager
) : UserRepository {

    private var user: DocumentSnapshot? = null
    private lateinit var userList: List<User>

    override suspend fun getUserById(uid: String): User {
        firestore.collection(Constants.USER_COLLECTION).document(uid).get().addOnSuccessListener {
            user = it
        }.addOnFailureListener { exception ->
            throw exception
        }
        return User(
            username = user?.getString("username")!!,
            fullName = user?.getString("fullName")!!,
            gender = user?.getString("gender")!!,
            email = user?.getString("email")!!,
            lat = user?.getDouble("lat"),
            lng = user?.getDouble("lng"),
            isAdBlocked = user?.getBoolean("isAdBlocked")!!,
            isPremium = user?.getBoolean("isPremium")!!,
            imageUrl = user?.getString("imageUrl")!!,
            status = user?.getString("status")!!,
            id = user?.getString("id")!!,
            credit = user?.get("credit") as Int,
            birthDate = user?.get("birthDate") as DateTime?,
            createDate = user?.getString("createDate")!!
        )
    }

    override suspend fun updateUserById(user: User) {
        firestore.collection(Constants.USER_COLLECTION).document(user.id).update(user.toHashMap())
            .addOnFailureListener { exception ->
                throw exception
            }
    }

    override suspend fun signInUser(user: User) {
        firestore.collection(Constants.USER_COLLECTION).add(user.toHashMap())
            .addOnFailureListener { exception ->
                throw exception
            }
    }

    override suspend fun getUsersWithFilter(): List<User> {
        val fromAge = dataStore.readInt(Constants.FILTER_FROM_AGE_KEY)
        val toAge = dataStore.readInt(Constants.FILTER_TO_AGE_KEY)
        val gender = dataStore.readString(Constants.FILTER_GENDER_KEY)
        val distance = dataStore.readInt(Constants.FILTER_DISTANCE_KEY)
        val status = dataStore.readString(Constants.FILTER_STATUS_KEY)

        val collection = firestore.collection(Constants.USER_COLLECTION)
        var task: Query = collection

        fromAge?.let {
            task = task.whereGreaterThanOrEqualTo("age", it)
        }
        toAge?.let {
            task = task.whereLessThanOrEqualTo("age", it)
        }
        gender?.let {
            if (!it.equals("Both"))
                task = task.whereEqualTo("gender", it)
        }
        distance?.let {
            // TODO("Calculate Distance")
        }
        status?.let {
            if (!it.equals("Any"))
                task = task.whereEqualTo("status", it)
        }

        task.get().addOnSuccessListener { snapshot ->
            userList = snapshot.map { it.toUser() }
        }.addOnFailureListener { exception ->
            throw exception
        }

        return userList
    }

    override suspend fun isMe(uid: String): Boolean {
        return currentUser.uid == uid
    }

    private fun QueryDocumentSnapshot.toUser(): User {
        return User(
            username = this.getString("username")!!,
            fullName = this.getString("fullName")!!,
            gender = this.getString("gender")!!,
            email = this.getString("email")!!,
            lat = this.getDouble("lat"),
            lng = this.getDouble("lng"),
            isAdBlocked = this.getBoolean("isAdBlocked")!!,
            isPremium = this.getBoolean("isPremium")!!,
            imageUrl = this.getString("imageUrl")!!,
            status = this.getString("status")!!,
            id = this.getString("id")!!,
            credit = this.get("credit") as Int,
            birthDate = this.get("birthDate") as DateTime?,
            createDate = this.getString("createDate")!!
        )
    }
}