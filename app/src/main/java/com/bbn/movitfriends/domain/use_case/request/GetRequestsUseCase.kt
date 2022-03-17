package com.bbn.movitfriends.domain.use_case.request

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.RequestRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.util.ArrayList
import javax.inject.Inject

class GetRequestsUseCase @Inject constructor(
    private val repository: RequestRepository,
    private val loginUser: FirebaseUser?
) {

    operator fun invoke(): Flow<Resource<MutableLiveData<ArrayList<User>>>> = flow {
        try {
            emit(Resource.Loading<MutableLiveData<ArrayList<User>>>())
            loginUser?.let { user ->
                val users = repository.getRequests()
                emit(Resource.Success<MutableLiveData<ArrayList<User>>>(users))
            }

        } catch (e: Exception){
            emit(Resource.Error<MutableLiveData<ArrayList<User>>>(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}