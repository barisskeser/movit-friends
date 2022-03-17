package com.bbn.movitfriends.presentation.login

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bbn.movitfriends.presentation.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){
    val state = viewModel.state.value


    viewModel.loginWithEmailAndPassword("", "")

    Text(text = "${state.error}")

    if(state.error == null && state.isLoggedIn){
        navController.navigate(Screen.MessageScreen.route)
    }

    state.error?.let {
        if(it.isNotBlank()){
            Log.d("LoginActivity", "LoginScreen: ${state.error}")
        }
    }


}