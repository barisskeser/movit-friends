package com.bbn.movitfriends.domain.repository

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.model.User
import java.util.ArrayList

interface RequestRepository {

    suspend fun getRequests(): MutableLiveData<ArrayList<User>>

}