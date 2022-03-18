package com.bbn.movitfriends.presentation.map

import com.bbn.movitfriends.domain.model.User

data class MapState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null
)