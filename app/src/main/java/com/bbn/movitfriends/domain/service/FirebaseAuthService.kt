package com.bbn.movitfriends.domain.service

interface FirebaseAuthService {

    fun isLoggedIn(): Boolean

    fun loginWithEmailAndPassword(email: String, password: String): Boolean

}