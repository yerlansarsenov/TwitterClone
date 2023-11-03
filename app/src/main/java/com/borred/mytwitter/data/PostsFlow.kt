package com.borred.mytwitter.data

import com.borred.mytwitter.model.PostModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

val postsFlow = MutableStateFlow(listOf<PostModel>())

fun addMockPost(
    text: String
) {
    postsFlow.update { list ->
        list.toMutableList().apply {
            add(
                PostModel(
                    username = "Yerlan Sarsenov",
                    date = "now",
                    text = text
                )
            )
        }
    }
}

fun deletePost(index: Int) {
    postsFlow.update { list ->
        list.toMutableList().apply {
            removeAt(index)
        }
    }
}

fun editPost(index: Int, text: String) {
    postsFlow.update { list ->
        val mutList = list.toMutableList()
        mutList[index] = mutList[index].copy(
            text = text
        )
        mutList
    }
}

fun likeMockPost(
    index: Int
) {
    val list = postsFlow.value
    list[index].likes.value = !list[index].likes.value
}
