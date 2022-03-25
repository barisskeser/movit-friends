package com.bbn.movitfriends.data.service

import android.util.Log
import com.bbn.movitfriends.domain.interfaces.RegisterCallBack
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.UserRepository
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class FirebaseAuthServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
) : FirebaseAuthService {

    private val TAG = "FirebaseAuthServiceImpl"

    override suspend fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String){
        val task = firebaseAuth.signInWithEmailAndPassword(email, password)

        if(!task.isSuccessful){
            throw Exception("Email or password is wrong!")
        }

    }

    override suspend fun createUserWithEmailAndPassword(
        registerCallBack: RegisterCallBack,
        email: String,
        password: String,
        fullName: String,
        username: String,
        gender: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { result ->
            result?.user?.let { createdUser ->
                val userID = createdUser.uid
                Log.d("FirebaseAuthService", "createUserWithEmailAndPassword: user created")
                val user = User(
                    id = userID,
                    email = email,
                    fullName = fullName,
                    username = username,
                    gender = gender,
                    age = 18
                )

                Log.d(TAG, "Success: User created with email and password.")
                registerCallBack.onRegisterCallBack(user)
            }
        }.addOnFailureListener {
            Log.d(TAG, "Failure: ${it.localizedMessage}")
            registerCallBack.onFailure(it.localizedMessage ?: "An unexpected error occured")
        }
    }
}