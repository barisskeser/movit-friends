package com.bbn.movitfriends.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.use_case.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    init {
        // It's for check to already login
        loginWithEmailAndPassword()
    }

    fun loginWithEmailAndPassword(email: String = "", password: String = "") {
        loginUseCase(email, password).onEach { result ->
            when (result){
                is Result.Success -> {
                    _state.value = LoginState(isLoggedIn = true)
                }
                is Result.Error -> {
                    _state.value = LoginState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

}