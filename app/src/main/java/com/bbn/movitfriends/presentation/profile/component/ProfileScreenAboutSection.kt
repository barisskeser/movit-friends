package com.bbn.movitfriends.presentation.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreenAboutSection(
    about: String
) {
    val scrollState = rememberScrollState(0)
    Column(modifier = Modifier
        .fillMaxHeight(0.40f)
        .verticalScroll(scrollState, true)) {
        Text(
            text = about,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )
    }
}