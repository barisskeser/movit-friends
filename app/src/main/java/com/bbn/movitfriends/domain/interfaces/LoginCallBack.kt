package com.bbn.movitfriends.domain.interfaces

interface LoginCallBack {
    fun onLogin(uid: String)

    fun onFailure(error: String)
}