package com.bbn.movitfriends.presentation.profile

import com.bbn.movitfriends.domain.model.User

data class ProfileState(
    val isMe: Boolean = false,
    val request: String = "", // accepted, waiting
    val user: User? = null
)