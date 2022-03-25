package com.bbn.movitfriends.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.common.UiEvent
import com.bbn.movitfriends.domain.use_case.login.LoginUseCase
import com.bbn.movitfriends.presentation.Screen
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginUser: FirebaseUser?
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        if(isLoginBefore())
            sendUiEvent(UiEvent.Navigate(Screen.ProfileScreen.route + "/${loginUser!!.uid}"))
    }

    fun onEvent(event: LoginEvent){
        when(event){
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

    private fun isLoginBefore(): Boolean{
        return loginUser != null
    }

    private fun loginWithEmailAndPassword(email: String = "", password: String = "") {
        sendUiEvent(UiEvent.ShowLoading)
        loginUseCase(email, password).onEach { result ->
            when (result){
                is Result.Success -> {
                    sendUiEvent(UiEvent.Navigate(Screen.ProfileScreen.route + "/${loginUser!!.uid}"))
                }
                is Result.Error -> {
                    sendUiEvent(UiEvent.ShowError(result.message ?: "An unexpected error occured!"))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch{
        _uiEvent.send(event)
    }
}