package com.bbn.movitfriends.domain.interfaces

import com.bbn.movitfriends.domain.model.User
import java.lang.Exception

interface UserCallBack {
    fun onCallBack(user: User)

    fun onFailure(exception: Exception)
}