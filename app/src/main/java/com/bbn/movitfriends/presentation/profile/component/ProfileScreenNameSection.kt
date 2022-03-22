package com.bbn.movitfriends.presentation.profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import androidx.navigation.NavController
import com.bbn.movitfriends.R
import com.bbn.movitfriends.presentation.Screen
import com.bbn.movitfriends.presentation.profile.*


private val actionButtonImage: MutableState<Int> = mutableStateOf(R.drawable.ic_message)

@Composable
fun ProfileScreenNameSection(
    name: String,
    status: String,
    navController: NavController,
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
                    actionButtonOnClick(
                        state = state,
                        navController = navController,
                        viewModel = viewModel
                    )
                }) {
                when {
                    state.value.requestState == "accepted" -> {
                        actionButtonImage.value = R.drawable.ic_message
                        Icon(
                            painter = painterResource(actionButtonImage.value),
                            contentDescription = "Message"
                        )
                    }
                    state.value.requestState.isBlank() -> {
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

fun actionButtonOnClick(
    state: State<ProfileState>,
    navController: NavController,
    viewModel: ProfileViewModel
) {
    state.value.user?.let {
        if (state.value.requestState == "accepted")
            navController.navigate(Screen.MessageScreen.route + "/${it.id}")
        else if (state.value.requestState.isBlank()){

            viewModel.sendRequest(it.id)
            if(state.value.error == null)
                actionButtonImage.value = R.drawable.ic_user_wait
        }
    }

}
