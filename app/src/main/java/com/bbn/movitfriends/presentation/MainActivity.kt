package com.bbn.movitfriends.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bbn.movitfriends.common.Constants
import com.bbn.movitfriends.presentation.chat.ChatScreen
import com.bbn.movitfriends.presentation.login.LoginScreen
import com.bbn.movitfriends.presentation.profile.ProfileScreen
import com.bbn.movitfriends.presentation.register.RegisterScreen
import com.bbn.movitfriends.presentation.ui.theme.MovitFriendsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovitFriendsTheme {
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