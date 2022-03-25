package com.bbn.movitfriends.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.UiEvent
import com.bbn.movitfriends.domain.interfaces.RegisterCallBack
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.UserRepository
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.bbn.movitfriends.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService,
    private val userRepository: UserRepository
) : ViewModel(), RegisterCallBack {

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnRegister -> {
                createUserWithEmailAndPassword(
                    email = event.email,
                    password = event.password,
                    fullName = event.fullName,
                    username = event.username,
                    gender = event.gender
                )
            }
        }
    }

    private fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        fullName: String,
        username: String,
        gender: String
    ) = viewModelScope.launch {

        sendUiEvent(UiEvent.ShowLoading)

        firebaseAuthService.createUserWithEmailAndPassword(
            this@RegisterViewModel,
            email = email,
            password = password,
            fullName = fullName,
            username = username,
            gender = gender
        )
    }

    private fun createUserInRepo(user: User) = viewModelScope.launch {
        userRepository.createUser(this@RegisterViewModel, user)
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    override fun onRegisterCallBack(user: User) {
        createUserInRepo(user)
    }

    override fun onCreateUserCallBack(userUid: String) {
        sendUiEvent(UiEvent.Navigate(Screen.ProfileScreen.route + "/$userUid"))
    }

    override fun onFailure(error: String) {
        sendUiEvent(UiEvent.ShowError(error))
    }
}