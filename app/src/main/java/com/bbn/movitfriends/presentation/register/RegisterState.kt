package com.bbn.movitfriends.presentation.register

data class RegisterState(
    val userUid: String? = null,
    val isDone: Boolean = false,
    val error: String? = null
)