package com.bbn.movitfriends.presentation.login

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.model.User
import java.util.ArrayList

data class LoginState(
    val isLoggedIn: Boolean = false,
    val uid: String? = null,
    val error: String? = null
)