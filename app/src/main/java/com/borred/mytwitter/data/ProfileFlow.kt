package com.borred.mytwitter.data

import com.borred.mytwitter.model.AccountModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

val profileFlow = MutableStateFlow(
    AccountModel(
        username = "SanSanich",
        bio = "Just for fun"
    )
)

fun changeUserName(name: String) {
    profileFlow.update {
        it.copy(username = name)
    }
}

fun changeBio(name: String) {
    profileFlow.update {
        it.copy(bio = name)
    }
}
