package com.bbn.movitfriends.domain.service

import com.bbn.movitfriends.domain.model.User

interface FirebaseAuthService {

    suspend fun loginWithEmailAndPassword(email: String, password: String)

    suspend fun signInWithEmailAndPassword(email: String, password: String, user: User)
}