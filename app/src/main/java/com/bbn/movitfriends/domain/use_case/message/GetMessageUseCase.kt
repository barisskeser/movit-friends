package com.bbn.movitfriends.domain.use_case.message

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {

    operator fun invoke(friendUid: String): Flow<Resource<MutableLiveData<ArrayList<Message>>>> = flow {
        try {
            emit(Resource.Loading<MutableLiveData<ArrayList<Message>>>())
            val messages = repository.getMessages(friendUid)
            emit(Resource.Success<MutableLiveData<ArrayList<Message>>>(messages))
        } catch (e: Exception){
            emit(Resource.Error<MutableLiveData<ArrayList<Message>>>(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

}