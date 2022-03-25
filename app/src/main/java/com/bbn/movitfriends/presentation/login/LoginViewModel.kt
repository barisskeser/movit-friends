package com.bbn.movitfriends.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.use_case.login.IsLoginUseCase
import com.bbn.movitfriends.domain.use_case.login.LoginUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val isLoginUseCase: IsLoginUseCase,
    private val loginUser: FirebaseUser?
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    init {
        isLogin()
    }

    private fun isLogin() = viewModelScope.launch{
        if(isLoginUseCase())
            _state.value = LoginState(isLoggedIn = true, uid = loginUser?.uid)
    }

    fun loginWithEmailAndPassword(email: String = "", password: String = "") {
        loginUseCase(email, password).onEach { result ->
            when (result){
                is Result.Success -> {
                    _state.value = LoginState(isLoggedIn = true, uid = loginUser?.uid)
                }
                is Result.Error -> {
                    _state.value = LoginState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}