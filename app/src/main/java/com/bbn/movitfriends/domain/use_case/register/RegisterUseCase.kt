package com.bbn.movitfriends.domain.use_case.register

import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
){

    operator fun invoke(email: String, password: String, fullName: String, username: String, gender: String): Flow<Result> = flow {
        try {
            firebaseAuthService.createUserWithEmailAndPassword(
                email, password, fullName, username, gender
            )
            emit(Result.Success())
        }catch (e: Exception){
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}