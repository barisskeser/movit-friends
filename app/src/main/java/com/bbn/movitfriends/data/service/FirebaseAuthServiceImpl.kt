package com.bbn.movitfriends.data.service

import android.util.Log
import com.bbn.movitfriends.domain.interfaces.LoginCallBack
import com.bbn.movitfriends.domain.interfaces.RegisterCallBack
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthService {

    private val TAG: String = "FirebaseAuthServiceImpl"

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String,
        loginCallBack: LoginCallBack
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            it.user?.let { it1 -> loginCallBack.onLogin(it1.uid) }
        }.addOnFailureListener {
            loginCallBack.onFailure(it.localizedMessage ?: "An unexpected error occured!")
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
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
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