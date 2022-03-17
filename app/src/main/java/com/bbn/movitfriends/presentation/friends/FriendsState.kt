package com.bbn.movitfriends.presentation.friends

import androidx.lifecycle.MutableLiveData
import com.bbn.movitfriends.domain.model.User
import java.util.ArrayList

data class FriendsState(
    val isLoading: Boolean = false,
    val friendList: MutableLiveData<ArrayList<User>>? = MutableLiveData<ArrayList<User>>(),
    val error: String? = null
)