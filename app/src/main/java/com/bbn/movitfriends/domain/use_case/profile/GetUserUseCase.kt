package com.bbn.movitfriends.domain.use_case.profile

import androidx.compose.runtime.MutableState
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.interfaces.UserCallBack
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    private lateinit var mutableUser: MutableState<User>
    private lateinit var mutableError: MutableState<Exception>

    operator fun invoke(uid: String, userCallBack: UserCallBack): Flow<Result> = flow {
        try {
            repository.getUserById(uid, userCallBack)
            emit(Result.Success())
        } catch (e: Exception){
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}