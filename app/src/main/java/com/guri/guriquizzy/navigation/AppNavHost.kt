package com.guri.guriquizzy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.guri.guriquizzy.ui.home.QuestionViewModel
import com.guri.guriquizzy.ui.screens.FinalScoreScreen
import com.guri.guriquizzy.ui.screens.HomeScreen
import kotlinx.serialization.Serializable
import com.guri.guriquizzy.ui.screens.SplashScreen

@Serializable
object QuestionScreen

@Serializable
data class FinalScoreScreen(val finalScore: Int)

@Serializable
object SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: QuestionViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreen,
        modifier = modifier
    ) {
        composable<SplashScreen> {
            SplashScreen(navController = navController)
        }
        composable<QuestionScreen> {
            HomeScreen(navController = navController, vm = viewModel)
        }
        composable<FinalScoreScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<FinalScoreScreen>()
            FinalScoreScreen(
                finalScore = args.finalScore,
                viewModel = viewModel,
                onNavigateToHomePage = {
                    viewModel.resetQuiz()
                    navController.popBackStack()
                }
            )
        }
    }
}