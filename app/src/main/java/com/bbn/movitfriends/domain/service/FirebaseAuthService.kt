package com.bbn.movitfriends.domain.service

import com.bbn.movitfriends.domain.model.User

interface FirebaseAuthService {

    suspend fun isLoggedIn(): Boolean

    suspend fun loginWithEmailAndPassword(email: String, password: String)

    suspend fun createUserWithEmailAndPassword(email: String, password: String, user: User)
}