package com.bbn.movitfriends.domain.use_case.message

import android.util.Log
import com.bbn.movitfriends.common.Resource
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.repository.MessageRepository
import com.bbn.movitfriends.common.Result
import com.bbn.movitfriends.domain.model.InvalidMessageException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {

    private val TAG: String? = this::class.simpleName

    suspend operator fun invoke(message: Message) {
        if (message.text.isNullOrBlank()){
            throw InvalidMessageException("The text of message can't be blank")
        }
        repository.sendMessage(message)
    }
}