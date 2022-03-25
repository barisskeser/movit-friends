package com.bbn.movitfriends.domain.use_case.register

import android.graphics.Bitmap
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
){

    operator fun invoke(user: User): Flow<Result> = flow {
        try {
            repository.createUser(user)
            emit(Result.Success())
        }catch (e: Exception){
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occured!"))
        }
    }

}