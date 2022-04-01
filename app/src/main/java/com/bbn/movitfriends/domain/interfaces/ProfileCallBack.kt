package com.bbn.movitfriends.domain.interfaces

import android.service.autofill.OnClickAction
import com.bbn.movitfriends.domain.model.User

interface ProfileCallBack {

    fun onCallBack(user: User)

    fun onFailure(error: String)

}
