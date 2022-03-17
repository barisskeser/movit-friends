package com.bbn.movitfriends.presentation.profile

import com.bbn.movitfriends.domain.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)