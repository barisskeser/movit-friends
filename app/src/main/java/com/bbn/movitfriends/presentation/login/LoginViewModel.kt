package com.bbn.movitfriends.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.UiEvent
import com.bbn.movitfriends.domain.interfaces.LoginCallBack
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.bbn.movitfriends.presentation.Screen
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService,
    private val loginUser: FirebaseUser?
) : ViewModel(), LoginCallBack {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        if (isLoginBefore())
            sendUiEvent(UiEvent.Navigate(Screen.ChatScreen.route))
            //sendUiEvent(UiEvent.Navigate(Screen.ProfileScreen.route + "/${loginUser!!.uid}"))
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLogin -> {
                loginWithEmailAndPassword(event.email, event.password)
            }

            is LoginEvent.OnCreateAccountClick -> {
                sendUiEvent(UiEvent.Navigate(Screen.RegisterScreen.route))
            }

            is LoginEvent.OnForgetPasswordClick -> {
                //TODO("Forget Password")
            }
        }
    }

    private fun isLoginBefore(): Boolean {
        return loginUser != null
    }

    private fun loginWithEmailAndPassword(email: String = "", password: String = "") =
        viewModelScope.launch {
            sendUiEvent(UiEvent.ShowLoading)
            firebaseAuthService.loginWithEmailAndPassword(email, password, this@LoginViewModel)
        }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    override fun onLogin(uid: String) {
        sendUiEvent(UiEvent.TerminateLoading)
        sendUiEvent(UiEvent.Navigate(Screen.ProfileScreen.route + "/$uid"))
    }

    override fun onFailure(error: String) {
        sendUiEvent(UiEvent.ShowError(error))
    }
}