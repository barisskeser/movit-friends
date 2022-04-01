package com.bbn.movitfriends.domain.use_case.request

import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.repository.RequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetRequestStateUseCase @Inject constructor(
    private val repository: RequestRepository
) {

    operator fun invoke(uid: String): Flow<Resource<String?>> = flow {
        try {
            emit(Resource.Loading<String?>())
            val state = repository.getRequestState(uid)
            emit(Resource.Success<String?>(state))
        } catch (e: Exception){
            emit(Resource.Error<String?>(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}