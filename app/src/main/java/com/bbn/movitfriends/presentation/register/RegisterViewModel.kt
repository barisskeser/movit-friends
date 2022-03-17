package com.bbn.movitfriends.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.use_case.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    fun signInWithEmailAndPassword(email: String, password: String, user: User){
        registerUseCase(email, password, user).onEach { result ->
            when(result){
                is Result.Success -> {
                    _state.value = RegisterState(isDone = true)
                }
                is Result.Error -> {
                    _state.value = RegisterState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

}