package com.bbn.movitfriends.data.service

import android.util.Log
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

    override suspend fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String){
        val task = firebaseAuth.signInWithEmailAndPassword(email, password)

        if(!task.isSuccessful){
            throw Exception("Email or password is wrong!")
        }

    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String, fullName: String, username: String, gender: String) {
        val task = firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { result ->
            result?.user?.let { createdUser ->
                val userID = createdUser.uid
                CoroutineScope(Dispatchers.IO).launch {
                    val user = User(
                        id = userID,
                        fullName = fullName,
                        username = username,
                        email = email,
                        gender = gender,
                        birthDate = null
                    )
                    userRepository.createUser(user)
                }
            }
        }

        if(!task.isSuccessful)
            task.exception?.let {
                throw it
            }
    }
}