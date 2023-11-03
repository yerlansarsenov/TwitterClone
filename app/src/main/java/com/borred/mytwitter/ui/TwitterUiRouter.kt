package com.borred.mytwitter.ui

sealed class Screen(val title: String, val route: String) {
    object Home : Screen("Home", "Home")
    object Post : Screen("Post", "Post")
    object Profile : Screen ("Profile", "Profile")
}