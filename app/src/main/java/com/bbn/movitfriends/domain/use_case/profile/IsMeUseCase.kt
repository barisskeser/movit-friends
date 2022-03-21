package com.bbn.movitfriends.domain.use_case.profile

import com.bbn.movitfriends.domain.repository.RequestRepository
import com.bbn.movitfriends.domain.repository.UserRepository
import javax.inject.Inject

class IsMeUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(uid: String): Boolean{
        return repository.isMe(uid)
    }

}