package com.bbn.movitfriends.presentation.profile

import com.bbn.movitfriends.domain.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val isMe: Boolean = false,
    val requestState: String = "",
    val user: User? = null,
    val error: String? = null
)