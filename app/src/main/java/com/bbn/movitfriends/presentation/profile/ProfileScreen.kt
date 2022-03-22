package com.bbn.movitfriends.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bbn.movitfriends.presentation.profile.component.ProfileScreenAboutSection
import com.bbn.movitfriends.presentation.profile.component.ProfileScreenImageSection
import com.bbn.movitfriends.presentation.profile.component.ProfileScreenNameSection
import com.bbn.movitfriends.presentation.profile.component.ProfileScreenTopBar

private val username: MutableState<String> = mutableStateOf("")

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {

    val state = viewModel.state

    Box(Modifier.fillMaxSize()){
        Column {
            ProfileScreenTopBar(
                username = username.value,
                state = state,
                navController = navController
            )
            Spacer(modifier = Modifier.height(1.dp))
            state.value.user?.let {user ->
                username.value = user.username

                ProfileScreenImageSection(path = user.imageUrl)
                Spacer(modifier = Modifier.height(4.dp))
                ProfileScreenNameSection(
                    name = user.fullName,
                    status = user.status,
                    navController = navController,
                    viewModel = viewModel,
                    state = state
                )
                Spacer(Modifier.height(8.dp))
                ProfileScreenAboutSection(user.about)
            }
        }

        if(state.value.error != null){
            Text(
                text = state.value.error!!,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}