package com.bbn.movitfriends.data.repository

import android.util.Log
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.interfaces.RegisterCallBack
import com.bbn.movitfriends.domain.interfaces.UserCallBack
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.model.toHashMap
import com.bbn.movitfriends.domain.repository.FilterDataStoreManager
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.storage.StorageReference
import com.google.type.DateTime
import java.lang.Exception
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storageReference: StorageReference,
    private val currentUser: FirebaseUser?,
    private val dataStore: FilterDataStoreManager
) : UserRepository {

    private val TAG = "UserRepositoryImpl"
    private lateinit var userList: List<User>

    override suspend fun getUserById(uid: String, userCallBack: UserCallBack) {
        firestore.collection(Constants.USER_COLLECTION).document(uid).get()
            .addOnSuccessListener { user ->
                user.let {
                    val response = User(
                        username = it.getString("username")!!,
                        fullName = it.getString("fullName")!!,
                        gender = it.getString("gender")!!,
                        email = it.getString("email")!!,
                        about = it.getString("about")!!,
                        isAdBlocked = it.getBoolean("isAdBlocked")!!,
                        isPremium = it.getBoolean("isPremium")!!,
                        imageUrl = it.getString("imageUrl")!!,
                        id = it.getString("id")!!,
                        createDate = it.getString("createDate")!!
                    )
                    userCallBack.onCallBack(response)
                }
            }.addOnFailureListener {
                userCallBack.onFailure(it)
            }
    }

    override suspend fun updateUserById(user: User) {
        val task = firestore.collection(Constants.USER_COLLECTION).document(user.id)
            .update(user.toHashMap())

        if (!task.isSuccessful)
            throw Exception("Something went wrong while updating! Try again.")
    }

    override suspend fun createUser(registerCallBack: RegisterCallBack, user: User) {
        firestore.collection(Constants.USER_COLLECTION).document(user.id)
            .set(user.toHashMap())
            .addOnSuccessListener {
                Log.d(TAG, "User created on firestore")
                registerCallBack.onCreateUserCallBack(user.id)
            }
            .addOnFailureListener {
                registerCallBack.onFailure(it.localizedMessage ?: "An unexpected error occured!")
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
        return currentUser?.uid == uid
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
            age = this.get("age") as Int,
            createDate = this.getString("createDate")!!
        )
    }
}