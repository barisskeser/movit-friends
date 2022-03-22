package com.bbn.movitfriends.data.service

import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.model.setId
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

    override suspend fun createUserWithEmailAndPassword(email: String, password: String, user: User) {
        val task = firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            it.user?.let { authUser -> user.setId(authUser.uid) }
            CoroutineScope(Dispatchers.IO).launch{
                userRepository.signInUser(user)
            }
        }

        if(!task.isSuccessful){
            throw Exception("Something went wrong!")
        }

    }
}