package com.mglabs.twopagetodo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mglabs.twopagetodo.domain.TodoTask
import com.mglabs.twopagetodo.ui.presentation.screens.DetailsScreen
import com.mglabs.twopagetodo.ui.presentation.screens.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = HomeScreenUIModel) {
        composable<HomeScreenUIModel> {
            HomeScreen(onNavigateToDetails = { todo: TodoTask ->
                navController.navigate(todo.transform())
            })
        }
        composable<DetailScreenUIModel> {
            DetailsScreen(onNavigateToHome = {
                navController.navigate(HomeScreenUIModel)
            })
        }
    }
}