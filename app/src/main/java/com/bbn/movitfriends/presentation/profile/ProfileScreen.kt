package com.bbn.movitfriends.presentation.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.presentation.Screen
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage


private val actionButtonImage: MutableState<Int> = mutableStateOf(R.drawable.ic_message)
private lateinit var state: ProfileState

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {

    state = updateState(viewModel = viewModel)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        state.user?.let {user ->
            TopBar(user.username)
            Spacer(modifier = Modifier.height(1.dp))
            ImageSection(path = user.imageUrl)
            Spacer(modifier = Modifier.height(4.dp))
            NameSection(
                name = user.fullName,
                uid = user.id,
                status = user.status,
                navController = navController,
                viewModel = viewModel
            )
            Spacer(Modifier.height(8.dp))
            AboutSection(user.about)
            Spacer(Modifier.height(8.dp))
            MapSection()
        }

    }
}

private fun updateState(viewModel: ProfileViewModel): ProfileState {
    return viewModel.state.value
}

@Composable
private fun TopBar(
    username: String
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
                modifier = Modifier.size(45.dp)
            )
            Text(
                text = username,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
        }

        if(!state.isMe)
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Settings",
                tint = Color.Black,
                modifier = Modifier.size(45.dp).clickable {
                    // TODO("Go to settings")
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
private fun NameSection(
    name: String,
    uid: String,
    status: String,
    navController: NavController,
    viewModel: ProfileViewModel
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

        if(!state.isMe){
            Button(
                modifier = Modifier
                    .padding(end = 15.dp)
                    .height(50.dp)
                    .clip(CircleShape),
                onClick = {
                    if (state.requestState == "accepted")
                        navController.navigate(Screen.MessageScreen.route + "/$uid")
                    else if (state.requestState.isBlank()){

                        viewModel.sendRequest(uid) // State changed

                        state = updateState(viewModel)

                        if(state.error == null)
                            actionButtonImage.value = R.drawable.ic_user_wait
                    }
                }) {
                when {
                    state.requestState == "accepted" -> {
                        actionButtonImage.value = R.drawable.ic_message
                        Icon(
                            painter = painterResource(actionButtonImage.value),
                            contentDescription = "Message"
                        )
                    }
                    state.requestState.isBlank() -> {
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
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )
    }

}

@Composable
private fun MapSection() {
    Box(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {

    }
}