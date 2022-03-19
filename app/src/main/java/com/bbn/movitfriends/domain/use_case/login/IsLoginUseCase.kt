package com.bbn.movitfriends.domain.use_case.login

import com.bbn.movitfriends.domain.service.FirebaseAuthService
import javax.inject.Inject

class IsLoginUseCase @Inject constructor(
    private val service: FirebaseAuthService
) {

    suspend operator fun invoke(): Boolean{
        return service.isLoggedIn()
    }

}