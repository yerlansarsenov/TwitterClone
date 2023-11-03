package com.borred.mytwitter.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.borred.mytwitter.MainViewModel
import com.borred.mytwitter.content.AppContent
import com.borred.mytwitter.content.PostDetails
import com.borred.mytwitter.content.ProfileScreen
import com.borred.mytwitter.data.postsFlow
import com.borred.mytwitter.ui.theme.TwitterCloneTheme

@Preview(showSystemUi = true)
@Composable
fun TwitterUiApp(viewModel: MainViewModel = MainViewModel()) {
    val universalNavController = rememberNavController()
    TwitterCloneTheme {
        Column {
            NavHost(
                navController = universalNavController,
                startDestination = Screen.Home.route,
                enterTransition = {
                    slideInHorizontally() + fadeIn()
                },
                exitTransition = {
                    slideOutHorizontally() + fadeOut()
                },
                modifier = Modifier.weight(1f)
            ) {
                composable(Screen.Home.route) {
                    AppContent(viewModel, universalNavController)
                }
                composable(
                    Screen.Post.route + "/{index}",
                    arguments = listOf(
                        navArgument("index"){
                            type = NavType.IntType
                        }
                    )
                ) {
                    val index = it.arguments?.getInt("index") ?: 0
                    val post = postsFlow.collectAsState().value[index]
                    PostDetails(
                        post = post,
                        universalNavHostController = universalNavController,
                        index = index
                    )
                }
                composable(route = Screen.Profile.route) {
                    ProfileScreen()
                }
            }
            Divider()
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf(
                    "Feed",
                    "Profile"
                ).forEach {
                    Text(
                        text = it,
                        modifier = Modifier
                            .clickable {
                                when (it) {
                                    "Feed" -> universalNavController.navigate(
                                        Screen.Home.route
                                    )
                                    "Profile" -> universalNavController.navigate(
                                        Screen.Profile.route
                                    )
                                }
                            }
                            .padding(vertical = 12.dp)
                            .weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
