package com.bbn.movitfriends.presentation.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bbn.movitfriends.R
import com.bbn.movitfriends.common.UiEvent
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

private val username: MutableState<String> = mutableStateOf("")
private val actionButtonImage: MutableState<Int> = mutableStateOf(R.drawable.ic_message)
private val error: MutableState<String> = mutableStateOf("")
private val loading: MutableState<Boolean> = mutableStateOf(false)

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {

    val state = viewModel.state

    Box(Modifier.fillMaxSize()){
        Column {
            TopBar(
                username = username.value,
                state = state,
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(1.dp))
            state.value.user?.let {user ->
                username.value = user.username

                ImageSection(path = user.imageUrl)
                Spacer(modifier = Modifier.height(4.dp))
                NameSection(
                    name = user.fullName,
                    status = user.status,
                    viewModel = viewModel,
                    state = state
                )
                Spacer(Modifier.height(8.dp))
                AboutSection(user.about)
            }
        }

        if(loading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        LaunchedEffect(key1 = true) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        navController.navigate(event.route)
                    }

                    is UiEvent.ShowError -> {
                        error.value = event.errorMessage
                    }

                    is UiEvent.ShowLoading -> {
                        loading.value = true
                    }

                    is UiEvent.TerminateLoading -> {
                        loading.value = false
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    username: String,
    state: State<ProfileState>,
    viewModel: ProfileViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfffafafa))
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(45.dp).clickable {
                    viewModel.onEvent(ProfileEvent.OnBackIconClicked)
                }
            )
            Text(
                text = username,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
        }

        if(state.value.isMe)
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Settings",
                tint = Color.Black,
                modifier = Modifier
                    .size(45.dp)
                    .clickable {
                        viewModel.onEvent(ProfileEvent.OnClickEdit)
                    }
            )
    }
}

@Composable
private fun ImageSection(
    path: String
) {
    /*Image(
        painter = image,
        contentDescription = null,
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .padding(top = 10.dp)

    )*/
    if (path == "default") {
        Image(
            painter = painterResource(id = R.drawable.sign_in_illustrator),
            contentDescription = "Profile Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .padding(horizontal = 5.dp, vertical = 10.dp)
        )
    } else
        GlideImage(
            imageModel = path,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .padding(horizontal = 5.dp, vertical = 10.dp),
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
}

@Composable
private fun AboutSection(
    about: String
) {
    val scrollState = rememberScrollState(0)
    Column(modifier = Modifier
        .fillMaxHeight(0.40f)
        .verticalScroll(scrollState, true)) {
        Text(
            text = about,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )
    }
}

@Composable
private fun NameSection(
    name: String,
    status: String,
    viewModel: ProfileViewModel,
    state: State<ProfileState>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ){
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = status,
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = if (status == "Online") Color.Green else Color.Red,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }

        if(!state.value.isMe){
            Button(
                modifier = Modifier
                    .padding(end = 15.dp)
                    .height(50.dp)
                    .clip(CircleShape),
                onClick = {
                    state.value.user?.let {
                        viewModel.onEvent(ProfileEvent.OnActionClicked(it.id))
                    }
                }) {
                when {
                    state.value.request == "accepted" -> {
                        actionButtonImage.value = R.drawable.ic_message
                        Icon(
                            painter = painterResource(actionButtonImage.value),
                            contentDescription = "Message"
                        )
                    }
                    state.value.request.isBlank() -> {
                        actionButtonImage.value = R.drawable.ic_add_user
                        Icon(
                            painter = painterResource(actionButtonImage.value),
                            contentDescription = "Add Friend"
                        )
                    }
                    else -> {
                        actionButtonImage.value = R.drawable.ic_user_wait
                        Icon(
                            painter = painterResource(actionButtonImage.value),
                            contentDescription = "Waiting"
                        )
                    }
                }
            }
        }
    }
}

