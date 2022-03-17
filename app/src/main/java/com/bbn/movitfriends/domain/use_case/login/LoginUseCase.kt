package com.bbn.movitfriends.domain.use_case.login

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.bbn.movitfriends.presentation.login.LoginState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
){

    operator fun invoke(email: String, password: String): Flow<Result> = flow{
        try {
            firebaseAuthService.loginWithEmailAndPassword(email, password)
            emit(Result.Success())
        }catch (e: Exception){
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}