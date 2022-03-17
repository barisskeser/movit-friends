package com.bbn.movitfriends.presentation.message

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MessageScreen (
    navController: NavController,
    viewModel: MessageViewModel = hiltViewModel()
){
    val TAG = "MessageScreen"
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        state.messageList?.let { messages ->
            // TODO("Add list")
        }
        if(state.error.isNotBlank()){
            Log.d(TAG, "MessageScreen: ${state.error}")
        }
    }
}