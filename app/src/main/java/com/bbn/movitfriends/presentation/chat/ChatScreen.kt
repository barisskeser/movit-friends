package com.bbn.movitfriends.presentation.chat

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.bbn.movitfriends.R
import com.bbn.movitfriends.common.UiEvent
import com.bbn.movitfriends.domain.model.Chat
import com.bbn.movitfriends.presentation.chat.component.ChatItem

private val chats: MutableState<List<Chat>> = mutableStateOf(emptyList())
private val loading = mutableStateOf(false)
private val error = mutableStateOf("")

@Composable
fun ChatScreen(
    lifecycleOwner: LifecycleOwner,
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        observeLiveData(
            lifecycleOwner = lifecycleOwner,
            viewModel = viewModel
        )
    }


    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(Modifier.fillMaxSize()) {
            items(chats.value) { chat ->
                ChatItem(
                    name = chat.name,
                    message = chat.lastMessage,
                    image = painterResource(id = R.drawable.ic_man),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(ChatEvent.OnChatClicked(chat.chatID, chat.userUid))
                        }
                )
            }
        }

        if (loading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        LaunchedEffect(key1 = true) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        Log.d("ChatScreen", "navigate: ${event.route}")
                        navController.navigate(event.route)
                    }

                    is UiEvent.ShowLoading -> {
                        loading.value = true
                    }

                    is UiEvent.TerminateLoading -> {
                        loading.value = false
                    }

                    is UiEvent.ShowError -> {
                        error.value = event.errorMessage
                    }

                    is UiEvent.NavigatePop -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

private fun observeLiveData(lifecycleOwner: LifecycleOwner, viewModel: ChatViewModel) {
    viewModel.chatsLiveData.observe(lifecycleOwner) {
        chats.value = it
    }
}