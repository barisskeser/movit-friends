package com.bbn.movitfriends.presentation.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bbn.movitfriends.domain.service.FirebaseAuthService
import com.bbn.movitfriends.presentation.MainActivity
import com.bbn.movitfriends.presentation.Screen
import com.bbn.movitfriends.presentation.message.MessageScreen
import com.bbn.movitfriends.presentation.ui.theme.MovitFriendsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuthService: FirebaseAuthService

    private lateinit var email: String
    private lateinit var password: String

    private val TAG: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        Log.d(TAG, "onCreate: ")
        loginCheck()

        setContent {
            MovitFriendsTheme {
                Box(modifier = Modifier.fillMaxSize()) {

                    Button(onClick = {
                        if(firebaseAuthService.loginWithEmailAndPassword(email, password)){
                            intentActivity(MainActivity::class.java)
                        }
                    }) {
                        Text(text = "Login")
                    }
                }
            }
        }



    }

    private fun loginCheck() {
        if (firebaseAuthService.isLoggedIn()) {

        }
        intentActivity(MainActivity::class.java)
    }

    private fun intentActivity(activity: Any){
        val intent = Intent(this, activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Log.d(TAG, "intentActivity: Go Main Activity")
        startActivity(intent)
        finish()
    }
}
