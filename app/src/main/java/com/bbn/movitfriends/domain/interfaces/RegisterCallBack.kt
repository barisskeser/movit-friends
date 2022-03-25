package com.bbn.movitfriends.domain.interfaces

import com.bbn.movitfriends.domain.model.User

interface RegisterCallBack {

    fun onRegisterCallBack(user: User)

    fun onCreateUserCallBack(userUid: String)

    fun onFailure(error: String)
}