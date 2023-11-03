package com.borred.mytwitter.content

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.borred.mytwitter.data.likeMockPost
import com.borred.mytwitter.model.PostModel

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
@Composable
fun PostDetails(
    index: Int,
    post: PostModel,
    universalNavHostController: NavHostController
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)) {
        Header(text = "Post Details")

        Post(
            post = post,
            modifier = Modifier
                .background(Color.Magenta)
        )
        Button(
            onClick = { likeMockPost(index) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (post.likes.value) {
                    Color.Red
                } else {
                    Color.Gray
                }
            ),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = if (post.likes.value) {
                    "Dislike"
                } else {
                    "Like"
                }
            )
        }
    }
}
