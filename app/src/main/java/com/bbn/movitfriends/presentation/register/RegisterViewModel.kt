package com.bbn.movitfriends.presentation.register

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.use_case.register.CreateUserUseCase
import com.bbn.movitfriends.domain.use_case.register.RegisterUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val currentUser: FirebaseUser?
): ViewModel() {

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    fun createUserWithEmailAndPassword(email: String, password: String, fullName: String, username: String, gender: String){
        registerUseCase(email, password, fullName, username, gender).onEach { result ->
            when(result){
                is Result.Success -> {
                    _state.value = RegisterState(isDone = true, userUid = currentUser?.uid)
                }
                is Result.Error -> {
                    _state.value = RegisterState(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun createUserInRepo(user: User) {

        createUserUseCase(user).onEach { result ->
            when(result){
                is Result.Success -> {
                    _state.value = RegisterState(isDone = true, userUid = currentUser?.uid)
                }
                is Result.Error -> {
                    _state.value = RegisterState(error = result.message)
                }
            }
        }
    }
}