package com.bbn.movitfriends.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.presentation.chat.ChatScreen
import com.bbn.movitfriends.presentation.chat.component.ChatItem
import com.bbn.movitfriends.presentation.login.LoginScreen
import com.bbn.movitfriends.presentation.map.MapScreen
import com.bbn.movitfriends.presentation.message.MessageScreen
import com.bbn.movitfriends.presentation.message.MessageViewModel
import com.bbn.movitfriends.presentation.profile.ProfileScreen
import com.bbn.movitfriends.presentation.register.RegisterScreen
import com.bbn.movitfriends.presentation.ui.theme.MovitFriendsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovitFriendsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route
                    ) {
                        composable(
                            route = Screen.LoginScreen.route
                        ) {
                            LoginScreen(navController)
                        }
                        composable(
                            route = Screen.RegisterScreen.route
                        ){
                            RegisterScreen(navController)
                        }
                        composable(
                            route = Screen.ProfileScreen.route + "/{${Constants.PARAM_USER_ID}}"
                        ){
                            ProfileScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ChatScreen.route
                        ){
                            ChatScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovitFriendsTheme {
        Greeting("Android")
    }
}