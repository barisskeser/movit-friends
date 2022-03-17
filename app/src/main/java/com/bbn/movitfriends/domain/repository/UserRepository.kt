package com.bbn.movitfriends.domain.repository

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.model.User
import java.util.*

interface UserRepository {

    suspend fun getUserById(uid: String): User?

    suspend fun updateUserById(user: User)
}