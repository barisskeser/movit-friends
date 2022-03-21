package com.bbn.movitfriends.domain.use_case.request

import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.RequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SendRequestUseCase @Inject constructor(
    private val repository: RequestRepository
) {

    operator fun invoke(uid: String): Flow<Result> = flow {
        try {
            repository.sendRequest(uid)
            emit(Result.Success())
        }catch (e: Exception){
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}