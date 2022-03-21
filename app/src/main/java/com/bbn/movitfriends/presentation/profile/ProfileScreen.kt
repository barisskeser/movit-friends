package com.bbn.movitfriends.presentation.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bbn.movitfriends.R


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar("barisskeser")
        Spacer(modifier = Modifier.height(1.dp))
        ImageSection(image = painterResource(id = R.drawable.ic_launcher_background))
        Spacer(modifier = Modifier.height(4.dp))
        NameSection("Barış Keser", "Online", false)
        Spacer(Modifier.height(8.dp))
        AboutSection("It's like a map based social media app. You can meet new people, if you want you can add and talk them.")
        Spacer(Modifier.height(8.dp))
        MapSection()
    }
}

@Composable
fun TopBar(
    username: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfffafafa))
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(45.dp)
            )
            Text(
                text = username,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Settings",
            tint = Color.Black,
            modifier = Modifier.size(45.dp)
        )
    }
}

@Composable
fun ImageSection(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .padding(top = 10.dp)

    )
}

@Composable
fun NameSection(
    name: String,
    status: String,
    isFriend: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ){
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = status,
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = if (status == "Online") Color.Green else Color.Red,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }

        Button(
            modifier = Modifier
                .padding(end = 15.dp)
                .height(50.dp)
                .clip(CircleShape),
            onClick = { /*TODO*/ }) {
            if (isFriend)
                Icon(painter = painterResource(id = R.drawable.ic_message), contentDescription = "Message")
            else
                Icon(imageVector = Icons.Default.Add, contentDescription = "Message")
        }
    }


}

@Composable
fun AboutSection(
    about: String
) {
    val scrollState = rememberScrollState(0)
    Column(modifier = Modifier.fillMaxHeight(0.40f).verticalScroll(scrollState, true)) {
        Text(
            text = about,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )
    }

}

@Composable
fun MapSection() {
    Box(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {

    }
}