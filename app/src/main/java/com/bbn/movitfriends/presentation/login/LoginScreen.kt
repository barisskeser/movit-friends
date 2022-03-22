package com.bbn.movitfriends.presentation.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bbn.movitfriends.R
import com.bbn.movitfriends.presentation.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.value


    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ImageSection()
        Spacer(modifier = Modifier.height(20.dp))
        InputSection(
            navController = navController,
            viewModel = viewModel
        )
    }

    state.error?.let {
        if (it.isNotBlank()) {
            Log.d("LoginActivity", "LoginScreen: ${state.error}")
        }
    }

    if (state.isLoggedIn) {
        navController.navigate(Screen.ChatScreen.route)
    }
}

@Composable
private fun ImageSection() {
    Image(
        painter = painterResource(id = R.drawable.sign_in_illustrator),
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
    )
}

@Composable
fun InputSection(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val error = remember { mutableStateOf("") }

    OutlinedTextField(
        value = email.value, onValueChange = {
            email.value = it
        },
        modifier = Modifier
            .fillMaxWidth(0.8f),
        placeholder = {
            Text(text = "E-Mail")
        }
    )
    Spacer(modifier = Modifier.height(5.dp))
    OutlinedTextField(
        value = password.value, onValueChange = {
            password.value = it
        },
        modifier = Modifier
            .fillMaxWidth(0.8f),
        placeholder = {
            Text(text = "Password")
        }
    )
    Text(
        text = error.value,
        fontSize = 15.sp,
        color = Color.Red,
        modifier = Modifier
            .fillMaxWidth(0.8f)
    )
    Spacer(modifier = Modifier.height(10.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(45.dp),
        onClick = {
            viewModel.loginWithEmailAndPassword(email = email.value, password = password.value)
            if (viewModel.state.value.error != null)
                error.value = "*" + viewModel.state.value.error!!
        },
    ) {
        Text(text = "LOGIN")
    }
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Create an account!",
        fontSize = 15.sp,
        color = Color.DarkGray,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.clickable {
            navController.navigate(Screen.RegisterScreen.route)
        }
    )
}