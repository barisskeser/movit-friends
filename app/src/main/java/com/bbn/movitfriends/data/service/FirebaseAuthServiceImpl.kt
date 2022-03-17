package com.bbn.movitfriends.data.service

import android.util.Log
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): FirebaseAuthService {

    private val TAG: String = this::class.java.simpleName

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun loginWithEmailAndPassword(email: String, password: String): Boolean {
        var result: Boolean = false
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            result = true
        }
        return result
    }

    fun loginWithGoogleAccount(){

    }
}