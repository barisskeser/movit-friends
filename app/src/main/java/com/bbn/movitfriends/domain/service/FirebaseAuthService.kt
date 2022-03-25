package com.bbn.movitfriends.domain.service

import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.interfaces.RegisterCallBack
import com.bbn.movitfriends.domain.model.User
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthService {

    suspend fun isLoggedIn(): Boolean

    suspend fun loginWithEmailAndPassword(email: String, password: String)

    suspend fun createUserWithEmailAndPassword(
        registerCallBack: RegisterCallBack,
        email: String, password:
        String, fullName: String,
        username: String,
        gender: String
    )

}