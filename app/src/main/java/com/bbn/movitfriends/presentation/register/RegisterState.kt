package com.bbn.movitfriends.presentation.register

data class RegisterState(
    val isDone: Boolean = false,
    val error: String? = null
)