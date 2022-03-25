package com.bbn.movitfriends.presentation.login

sealed class LoginEvent {

    data class OnLogin(val email: String, val password: String): LoginEvent()

    object OnCreateAccountClick : LoginEvent()

    data class OnForgetPasswordClick(val email: String): LoginEvent()
}
