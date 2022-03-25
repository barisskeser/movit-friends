package com.bbn.movitfriends.domain.use_case.login

import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.service.FirebaseAuthService
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
            println(e)
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}