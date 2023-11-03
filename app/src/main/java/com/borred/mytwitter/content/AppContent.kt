package com.borred.mytwitter.content

import android.annotation.SuppressLint
import android.preference.PreferenceActivity.Header
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.borred.mytwitter.MainViewModel
import com.borred.mytwitter.data.addMockPost
import com.borred.mytwitter.data.changeUserName
import com.borred.mytwitter.data.deletePost
import com.borred.mytwitter.data.editPost
import com.borred.mytwitter.data.postsFlow
import com.borred.mytwitter.data.profileFlow
import com.borred.mytwitter.model.PostModel
import com.borred.mytwitter.ui.Screen

@Stable
sealed interface AddPostDialog {

    object Null : AddPostDialog

    object New : AddPostDialog

    data class Edit(
        val index: Int,
        val text: String
    ) : AddPostDialog
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
@Composable
fun AppContent(
    viewModel: MainViewModel,
    universalNavHostController: NavHostController
) {
    var isDialog by remember {
        mutableStateOf<AddPostDialog>(AddPostDialog.Null)
    }
    var isEditDialog by remember {
        mutableStateOf(-1 to "")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Header(text = "Posts", modifier = Modifier.weight(1f))
            Button(onClick = { isDialog = AddPostDialog.New }) {
                Text(text = "Add Post")
            }
        }
        val posts by postsFlow.collectAsState()
        LazyColumn {
            itemsIndexed(posts) { index, post ->
                Post(
                    post = post,
                    modifier = Modifier
                        .combinedClickable(
                            onLongClick = {
                                isEditDialog = index to post.text
                            },
                            onClick = {
                                universalNavHostController.navigate(
                                    Screen.Post.route + "/" + index
                                )
                            }
                        )
                )
            }
        }
    }
    if (isDialog != AddPostDialog.Null) {
        Dialog(
            onDismissRequest = { isDialog = AddPostDialog.Null }
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Yellow)
                    .padding(16.dp)
            ) {
                Text(text = "Write Post")
                var newName by remember {
                    mutableStateOf(
                        when (val d = isDialog) {
                            is AddPostDialog.Edit -> d.text
                            AddPostDialog.New -> ""
                            AddPostDialog.Null -> ""
                        }
                    )
                }
                TextField(
                    value = newName,
                    onValueChange = { newName = it }
                )
                Button(
                    onClick = {
                        when (val d = isDialog) {
                            is AddPostDialog.Edit -> editPost(
                                index = d.index,
                                text = newName
                            )
                            AddPostDialog.New -> addMockPost(
                                text = newName
                            )
                            AddPostDialog.Null -> Unit
                        }
                        isDialog = AddPostDialog.Null
                    }
                ) {
                    Text(text = "Post")
                }
            }
        }
    }
    if (isEditDialog.first != -1) {
        Dialog(
            onDismissRequest = { isEditDialog = -1 to "" }
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Yellow)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        deletePost(isEditDialog.first)
                        isEditDialog = -1 to ""
                    }
                ) {
                    Text(text = "Delete")
                }
                Button(
                    onClick = {
                        isDialog = AddPostDialog.Edit(
                            index = isEditDialog.first,
                            text = isEditDialog.second
                        )
                        isEditDialog = -1 to ""
                    }
                ) {
                    Text(text = "Edit")
                }
            }
        }
    }
}

@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = text, style = MaterialTheme.typography.h3)
}

@Composable
fun Post(
    post: PostModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(24.dp)) {
        Row {
            Text(text = "Author: ", color = Color.Gray)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = profileFlow.collectAsState().value.username)
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            Text(text = "Content: ", color = Color.Gray)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = post.text)
        }
        Divider()
    }
}
