package com.bbn.movitfriends.domain.use_case.profile

import com.bbn.movitfriends.domain.model.InvalidMessageException
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.domain.repository.UserRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(user: User) {
        repository.updateUserById(user)
    }

}