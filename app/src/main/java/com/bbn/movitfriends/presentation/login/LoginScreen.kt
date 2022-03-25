package com.bbn.movitfriends.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bbn.movitfriends.R
import com.bbn.movitfriends.common.UiEvent
import com.bbn.movitfriends.presentation.Screen
import kotlinx.coroutines.flow.collect

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val error = remember { mutableStateOf("") }
    val loading = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ImageSection()
            Spacer(modifier = Modifier.height(20.dp))
            InputSection(
                navController = navController,
                viewModel = viewModel,
                error = error
            )
        }

        LaunchedEffect(key1 = true){
            viewModel.uiEvent.collect { event ->
                when(event){
                    is UiEvent.Navigate -> {
                        loading.value = false
                        navController.navigate(event.route)
                    }

                    is UiEvent.ShowError -> {
                        loading.value = false
                        error.value = event.errorMessage
                    }

                    is UiEvent.ShowLoading -> {
                        loading.value = true
                    }
                }
            }
        }

        if (loading.value)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }

}

@Composable
private fun ImageSection() {
    Image(
        painter = painterResource(id = R.drawable.sign_in_illustrator),
        contentDescription = "LoginIllustration",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
    )
}

@Composable
private fun InputSection(
    navController: NavController,
    viewModel: LoginViewModel,
    error: MutableState<String>
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }

    // E-mail Text Field
    OutlinedTextField(
        value = email.value, onValueChange = {
            email.value = it
        },
        modifier = Modifier
            .fillMaxWidth(0.8f),
        placeholder = {
            Text(text = "E-Mail")
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true
    )
    Spacer(modifier = Modifier.height(5.dp))

    // Password Text Field
    OutlinedTextField(
        value = password.value, onValueChange = {
            password.value = it
        },
        modifier = Modifier
            .fillMaxWidth(0.8f),
        placeholder = {
            Text(text = "Password")
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                viewModel.onEvent(LoginEvent.OnLogin(email.value, password.value))
            }
        ),
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation()
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
            viewModel.onEvent(LoginEvent.OnLogin(email.value, password.value))
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