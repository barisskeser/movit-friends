package com.bbn.movitfriends.presentation.profile.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.bbn.movitfriends.R
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileScreenImageSection(
    path: String
) {
    /*Image(
        painter = image,
        contentDescription = null,
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .padding(top = 10.dp)

    )*/
    if (path == "default") {
        Image(
            painter = painterResource(id = R.drawable.sign_in_illustrator),
            contentDescription = "Profile Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .padding(horizontal = 5.dp, vertical = 10.dp)
        )
    } else
        GlideImage(
            imageModel = path,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .padding(horizontal = 5.dp, vertical = 10.dp),
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.LightGray,
                durationMillis = 350,
                dropOff = 0.65f,
                tilt = 20f
            ),
            failure = {
                Text(text = "image request failed.")
            })
}