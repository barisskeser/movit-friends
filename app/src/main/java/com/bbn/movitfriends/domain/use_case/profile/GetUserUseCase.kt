package com.bbn.movitfriends.domain.use_case.profile

import com.bbn.movitfriends.common.Resource
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

    operator fun invoke(uid: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val user = repository.getUserById(uid)
            user?.let { emit(Resource.Success<User>(user)) }
        } catch (e: Exception){
            emit(Resource.Error<User>(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}