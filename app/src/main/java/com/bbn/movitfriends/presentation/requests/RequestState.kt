package com.bbn.movitfriends.presentation.requests

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.model.User
import java.util.ArrayList

data class RequestState(
    val isLoading: Boolean = false,
    val requestList: MutableLiveData<ArrayList<User>>? = MutableLiveData<ArrayList<User>>(),
    val error: String? = null
)