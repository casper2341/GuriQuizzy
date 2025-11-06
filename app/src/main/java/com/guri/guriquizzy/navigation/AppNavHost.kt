package com.guri.guriquizzy.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Destination(val route: String)

val Home = Destination("home")
val Details = Destination("details")

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Destination = Home
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        composable(Home.route) {
            HomeScreen(onNavigateToDetails = {
                navController.navigate(Details.route)
            })
        }
        composable(Details.route) {
            DetailsScreen(onBack = { navController.popBackStack() })
        }
    }
}

@Composable
private fun HomeScreen(onNavigateToDetails: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen")
        Button(onClick = onNavigateToDetails) {
            Text(text = "Go to Details")
        }
    }
}

@Composable
private fun DetailsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Details Screen")
        Button(onClick = onBack) {
            Text(text = "Back")
        }
    }
}


