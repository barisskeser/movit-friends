package com.bbn.movitfriends.domain.use_case.profile

import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading<List<User>>())
            val users = repository.getUsersWithFilter()
            emit(Resource.Success<List<User>>(users))
        } catch (e: Exception){
            emit(Resource.Error<List<User>>(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}