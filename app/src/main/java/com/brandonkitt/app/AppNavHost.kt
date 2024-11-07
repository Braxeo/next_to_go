package com.brandonkitt.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.brandonkitt.nexttogo.mvi.views.NextToGoView

@Composable
fun MyAppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "next_to_go") {
        composable("next_to_go") { NextToGoView() }
        // Add more destinations as needed
    }
}