package com.bbn.movitfriends.presentation.map

import androidx.lifecycle.ViewModel
import com.bbn.movitfriends.common.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val networkHelper: NetworkHelper
): ViewModel() {

    init {
        println(
            if(networkHelper.isNetworkConnected())
                "Network Connected"
            else
                "Network Not Connected"
        )
    }

}