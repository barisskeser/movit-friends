package com.bbn.movitfriends.presentation.register

sealed class RegisterEvent {

    data class OnRegister(
        val fullName: String,
        val username: String,
        val email: String,
        val gender: String,
        val password: String
    ) : RegisterEvent()

}
