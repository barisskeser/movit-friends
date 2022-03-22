package com.bbn.movitfriends.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bbn.movitfriends.R

@Composable
fun RegisterScreen(
    navController: NavController
){
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageSection()
        Spacer(modifier = Modifier.height(15.dp))
        InputSection(navController = navController)
    }
}

@Composable
private fun ImageSection() {
    Image(
        painter = painterResource(id = R.drawable.register_illustration),
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    )
}

@Composable
fun InputSection(
    navController: NavController
) {

}