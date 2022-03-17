package com.bbn.movitfriends.domain.use_case.chat

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.model.Chat
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.ChatRepository
import com.bbn.movitfriends.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val repository: ChatRepository
) {

    operator fun invoke(): Flow<Resource<MutableLiveData<ArrayList<Chat>>>> = flow {
        try {
            emit(Resource.Loading<MutableLiveData<ArrayList<Chat>>>())
            val chats = repository.getChats()
            emit(Resource.Success<MutableLiveData<ArrayList<Chat>>>(chats))
        } catch (e: Exception) {
            Resource.Error<MutableLiveData<ArrayList<User>>>(e.localizedMessage ?: "An unexpected error occured")
        }
    }

}