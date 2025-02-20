package com.example.feedthesnake.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedthesnake.ui.presentation.GameOverScreen
import com.example.feedthesnake.ui.presentation.GameScreen
import com.example.feedthesnake.ui.presentation.HomeScreen
import com.example.feedthesnake.ui.presentation.NameEntryScreen
import com.example.feedthesnake.ui.presentation.ScoreTableScreen
import com.example.feedthesnake.ui.presentation.SecondSplashScreen
import com.example.feedthesnake.ui.presentation.SplashScreen
import com.example.feedthesnake.viewModel.SnakeViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(onNavigateToSecondSplash = { navController.navigate("second_splash") }
            )
        }
        composable("second_splash") {
            SecondSplashScreen(onNavigateToHome = { navController.navigate("home") }
            )
        }
        composable("home") {
            HomeScreen(onNavigateToNameEntry = {
                navController.navigate("name_entry")
            }, onNavigateToScoreTable = {
                navController.navigate(
                    "score_table"
                )

            })
        }
        composable("name_entry") {
            NameEntryScreen(
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToGame = { name ->
                    navController.navigate("game/$name")
                }
            )
        }

        composable("game/{name}") { backStackEntry ->
            val snakeViewModel: SnakeViewModel = hiltViewModel()
            val name = backStackEntry.arguments?.getString("name")

            if (name != null) {
                GameScreen(
                    name = name,
                    onNavigateToGameOver = { score ->
                        navController.navigate("game_over/$score")
                    },
                    snakeViewModel = snakeViewModel
                )
            }
        }

        composable("game_over/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            GameOverScreen(score = score, onNavigateToHome = { navController.navigate("home") },
                onNavigateToNameEntry = {
                    navController.navigate("name_entry")
                }
            )
        }
        composable("score_table") {

            val snakeViewModel: SnakeViewModel = hiltViewModel()
            ScoreTableScreen(
                onNavigateToHome = { navController.navigate("home") }, snakeViewModel
            )

        }
    }
}

