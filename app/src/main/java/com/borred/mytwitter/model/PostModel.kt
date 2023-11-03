package com.borred.mytwitter.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

data class PostModel(
    val username: String,
    val date: String,
    val text: String,
    val likes: MutableState<Boolean> = mutableStateOf(false),
    val comments: MutableState<Int> = mutableIntStateOf(0),
    val views: MutableState<Int> = mutableIntStateOf(0)
)