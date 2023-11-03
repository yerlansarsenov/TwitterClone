package com.borred.mytwitter.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.borred.mytwitter.data.changeUserName
import com.borred.mytwitter.data.postsFlow
import com.borred.mytwitter.data.profileFlow
import com.borred.mytwitter.ui.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val profile = profileFlow.collectAsState()
    var isDialog by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Header(text = "Profile")
        Text(
            modifier = Modifier
                .padding(24.dp)
                .clickable {
                    isDialog = true
                },
            text = profile.value.username,
            style = MaterialTheme.typography.h4
        )
        Divider()
        Text(
            modifier = Modifier.padding(12.dp),
            text = profile.value.bio,
            style = MaterialTheme.typography.body2
        )
        Divider()
        Row {
            Text(text = "followers: ", color = Color.Gray)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = profile.value.followers.value.toString())
        }
        Divider()
        Row {
            Text(text = "followings: ", color = Color.Gray)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = profile.value.following.value.toString())
        }
    }
    if (isDialog) {
        Dialog(
            onDismissRequest = { isDialog = false }
        ) {
            Column(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .padding(16.dp)
            ) {
                Text(text = "change user name")
                var newName by remember {
                    mutableStateOf(profile.value.username)
                }
                TextField(
                    value = newName,
                    onValueChange = { newName = it }
                )
                Button(
                    onClick = {
                        changeUserName(newName)
                        isDialog = false
                    }
                ) {
                    Text(text = "Apply")
                }
            }
        }
    }
}
