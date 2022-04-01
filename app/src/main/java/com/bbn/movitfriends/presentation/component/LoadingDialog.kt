package com.bbn.movitfriends.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDialog(
    text: String,
    acceptButtonText: String
) {
    Box(
        Modifier
            .fillMaxSize(0.25f)
            .border(1.dp, Color.White, RectangleShape)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(text = text, Modifier.padding(24.dp))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    
                }) {
                    Text(text = "Close")
                }
                TextButton(onClick = {

                }) {
                    Text(text = acceptButtonText)
                }
            }
        }
    }
}