package com.bbn.movitfriends.presentation.message

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.bbn.movitfriends.R
import com.bbn.movitfriends.common.UiEvent
import com.bbn.movitfriends.domain.model.Chat
import com.bbn.movitfriends.domain.model.Message
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.presentation.Screen
import com.bbn.movitfriends.presentation.message.component.MessageItem
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.collect

private var messages: MutableState<List<Message>> = mutableStateOf(emptyList())

@Composable
fun MessageScreen(
    lifecycleOwner: LifecycleOwner,
    navController: NavController,
    viewModel: MessageViewModel = hiltViewModel()
) {

    val otherUser = viewModel.otherUser
    val uiEvent = viewModel.uiEvent

    LaunchedEffect(key1 = true) {
        onObserveData(
            lifecycleOwner = lifecycleOwner,
            viewModel = viewModel
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            TopBar(
                navController = navController,
                user = otherUser
            )
            LazyColumn {
                items(messages.value) { message ->
                    MessageItem(
                        name = message.from,
                        message = message.text,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        LaunchedEffect(key1 = true) {
            uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        navController.navigate(event.route)
                    }

                    is UiEvent.NavigatePop -> {
                        navController.popBackStack()
                    }

                    is UiEvent.ShowError -> {

                    }

                    is UiEvent.ShowLoading -> {

                    }

                    is UiEvent.TerminateLoading -> {

                    }
                }
            }
        }
    }
}

private fun onObserveData(
    lifecycleOwner: LifecycleOwner,
    viewModel: MessageViewModel
) {
    viewModel.messageLiveData.observe(lifecycleOwner, Observer {
        messages.value = it
    })
}

@Composable
private fun TopBar(
    navController: NavController,
    user: User
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.ProfileScreen.route + "/${user.id}")
            }) {

        GlideImage(
            imageModel = user.imageUrl,
            modifier = Modifier,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.LightGray,
                durationMillis = 350,
                dropOff = 0.65f,
                tilt = 20f
            ),
            failure = {
                Text(text = "image request failed.")
            })

        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = user.fullName,
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}