package com.android.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.sharedelementtransition.ui.theme.SharedElementTransitionTheme


@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharedElementTransitionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SharedTransitionLayout {
                        NavHost(
                            navController = navController,
                            startDestination = "list"
                        ) {
                            composable("list") {
                                ListScreen(
                                    onItemClick = { animal ->
                                        navController.navigate("detail/${animal.img}/${animal.description}/${animal.name}")
                                    },
                                    animatedVisibilityScope = this
                                )
                            }
                            composable(
                                route = "detail/{resId}/{text}/{name}",
                                arguments = listOf(
                                    navArgument("resId") {
                                        type = NavType.IntType
                                    },
                                    navArgument("text") {
                                        type = NavType.StringType
                                    },
                                    navArgument("name") {
                                        type = NavType.StringType
                                    },
                                )
                            ) {
                                val resId = it.arguments?.getInt("resId") ?: 0
                                val text = it.arguments?.getString("text") ?: ""
                                val name = it.arguments?.getString("name") ?: ""
                                DetailScreen(
                                    resId = resId,
                                    text = text,
                                    name = name,
                                    animatedVisibilityScope = this
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SharedElementTransitionTheme {
        Greeting("Android")
    }
}