package com.bbn.movitfriends.domain.repository

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.domain.interfaces.RegisterCallBack
import com.bbn.movitfriends.domain.interfaces.UserCallBack
import com.bbn.movitfriends.domain.model.User
import java.util.*
import kotlin.collections.ArrayList

interface UserRepository {

    suspend fun getUserById(uid: String, userCallBack: UserCallBack)

    suspend fun updateUserById(user: User)

    suspend fun createUser(registerCallBack: RegisterCallBack, user: User)

    suspend fun getUsersWithFilter(): List<User>

    suspend fun isMe(uid: String): Boolean
}