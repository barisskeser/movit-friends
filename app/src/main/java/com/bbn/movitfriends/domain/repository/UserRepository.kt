package com.bbn.movitfriends.domain.repository


import com.bbn.movitfriends.domain.interfaces.*
import com.bbn.movitfriends.domain.model.User

interface UserRepository {

    fun getUserById(uid: String, userCallBack: UserCallBack)

    suspend fun updateUserById(user: User)

    suspend fun createUser(registerCallBack: RegisterCallBack, user: User)

    suspend fun getUsersWithFilter(): List<User>

    suspend fun isMe(uid: String): Boolean
}