package com.bbn.movitfriends.presentation.login

import android.util.Log
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
import com.bbn.movitfriends.presentation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
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
            LaunchedEffect(scaffoldState.snackbarHostState){
                scaffoldState.snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = "Retry message"
                )
            }
        }
    }

    if (state.isLoggedIn) {
        state.uid?.let {
            Log.d("LoginScreen", "LoginScreen: route")
            LaunchedEffect(scaffoldState){
                navController.navigate(Screen.ProfileScreen.route + "/$it")
            }
        }
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
    viewModel: LoginViewModel
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val error = remember { mutableStateOf("") }
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
                login(
                    email = email.value,
                    password = password.value,
                    viewModel = viewModel,
                    error = error
                )
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
            login(
                email = email.value,
                password = password.value,
                viewModel = viewModel,
                error = error
            )
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

private fun login(
    email: String,
    password: String,
    viewModel: LoginViewModel,
    error: MutableState<String>
){
    viewModel.loginWithEmailAndPassword(email = email, password = password)
    if (viewModel.state.value.error != null)
        error.value = "*" + viewModel.state.value.error!!
}