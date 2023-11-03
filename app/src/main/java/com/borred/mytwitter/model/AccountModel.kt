package com.borred.mytwitter.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class AccountModel(
    val username: String,
    val bio : String,
    val following : MutableState<Int> = mutableIntStateOf(0),
    val followers : MutableState<Int> = mutableIntStateOf(0),
    val posts : SnapshotStateList<PostModel> = mutableStateListOf()
)