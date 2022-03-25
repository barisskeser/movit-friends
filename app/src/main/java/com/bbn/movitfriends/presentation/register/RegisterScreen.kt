package com.bbn.movitfriends.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bbn.movitfriends.R
import com.bbn.movitfriends.domain.model.User
import com.bbn.movitfriends.presentation.Screen

@Composable
fun RegisterScreen(
    navController: NavController
) {

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageSection()
        Spacer(modifier = Modifier.height(15.dp))
        InputSection(
            navController = navController
        )
    }
}

@Composable
private fun ImageSection() {
    Image(
        painter = painterResource(id = R.drawable.register_illustration),
        contentDescription = "RegisterIllustration",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
    )
}

@Composable
private fun InputSection(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val fullName = remember { mutableStateOf("Barış Keser") }
    val username = remember { mutableStateOf("barisskeser") }
    val email = remember { mutableStateOf("barisskeser@hotmail.com") }
    val gender = remember { mutableStateOf("Man") }
    val password = remember { mutableStateOf("baris123") }
    val confirmPassword = remember { mutableStateOf("baris123") }
    val error = remember { mutableStateOf("") }
    val state = viewModel.state

    // Error Text
    Text(
        text = error.value,
        fontSize = 15.sp,
        color = Color.Red,
        modifier = Modifier
            .fillMaxWidth(0.8f)
    )
    Spacer(modifier = Modifier.height(5.dp))

    //Full Name
    CustomTextField(
        text = fullName,
        modifier = Modifier.fillMaxWidth(0.8f),
        placeHolder = "Full Name",
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions { },
        visualTransformation = VisualTransformation.None
    )
    Spacer(modifier = Modifier.height(5.dp))

    //Username
    CustomTextField(
        text = username,
        modifier = Modifier.fillMaxWidth(0.8f),
        placeHolder = "Username",
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions { },
        visualTransformation = VisualTransformation.None
    )
    Spacer(modifier = Modifier.height(5.dp))

    //E-Mail
    CustomTextField(
        text = email,
        modifier = Modifier.fillMaxWidth(0.8f),
        placeHolder = "E-Mail",
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions { },
        visualTransformation = VisualTransformation.None
    )
    Spacer(modifier = Modifier.height(5.dp))

    GenderSection(gender = gender)
    Spacer(modifier = Modifier.height(5.dp))

    //Password
    CustomTextField(
        text = password,
        modifier = Modifier.fillMaxWidth(0.8f),
        placeHolder = "Password",
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions { },
        visualTransformation = PasswordVisualTransformation()
    )
    Spacer(modifier = Modifier.height(5.dp))

    //Confirm Password
    CustomTextField(
        text = confirmPassword,
        modifier = Modifier.fillMaxWidth(0.8f),
        placeHolder = "Confirm Password",
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions { },
        visualTransformation = PasswordVisualTransformation()
    )
    Spacer(modifier = Modifier.height(10.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(45.dp),
        onClick = {
            if (isValid(
                    fullName = fullName.value,
                    username = username.value,
                    email = email.value,
                    gender = gender.value,
                    password = password.value,
                    confirmPassword = confirmPassword.value
                )
            ) {
                error.value = ""
                val user = createUser(
                    id = "",
                    fullName = fullName.value,
                    username = username.value,
                    email = email.value,
                    gender = gender.value,
                )
                viewModel.createUserWithEmailAndPassword(
                    fullName = fullName.value,
                    username = username.value,
                    email = email.value,
                    password = password.value,
                    gender = gender.value
                )
                if (state.value.error != null)
                    error.value = "*${state.value.error}"
                else {
                    println(state.value.userUid)
                    navController.navigate(Screen.ProfileScreen.route + "/${state.value.userUid}")
                }
            } else
                error.value = "*You must complete all fields"
        },
    ) {
        Text(text = "REGISTER")
    }

}

private fun createUser(
    id: String,
    fullName: String,
    username: String,
    email: String,
    gender: String
): User {
    return User(
        id = id,
        fullName = fullName,
        username = username,
        email = email,
        gender = gender,
        birthDate = null
    )
}

private fun isValid(
    fullName: String,
    username: String,
    email: String,
    gender: String,
    password: String,
    confirmPassword: String
): Boolean {
    if (fullName.isBlank() || username.isBlank() || email.isBlank() || gender.isBlank() || password.isBlank() || confirmPassword.isBlank())
        return false
    return true
}

@Composable
private fun CustomTextField(
    text: MutableState<String>,
    modifier: Modifier,
    placeHolder: String,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation
) {
    // Password Text Field
    OutlinedTextField(
        value = text.value, onValueChange = {
            text.value = it
        },
        modifier = modifier,
        placeholder = {
            Text(text = placeHolder)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation
    )
}

@Composable
fun GenderSection(
    gender: MutableState<String>
) {

    val manId = remember { mutableStateOf(R.drawable.ic_man) }
    val womanId = remember { mutableStateOf(R.drawable.ic_woman) }

    Row(
        Modifier.fillMaxWidth(0.8f),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Gender:")
        Spacer(modifier = Modifier.fillMaxWidth(0.1f))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = manId.value),
                contentDescription = "Man",
                modifier = Modifier
                    .size(56.dp)
                    .noRippleClickable {
                        manId.value = R.drawable.ic_man_selected
                        womanId.value = R.drawable.ic_woman
                        gender.value = "Man"
                    }
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.15f))
            Image(
                painter = painterResource(id = womanId.value),
                contentDescription = "Woman",
                modifier = Modifier
                    .size(56.dp)
                    .noRippleClickable {
                        womanId.value = R.drawable.ic_woman_selected
                        manId.value = R.drawable.ic_man
                        gender.value = "Woman"
                    }

            )
        }
    }
}

private inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    }