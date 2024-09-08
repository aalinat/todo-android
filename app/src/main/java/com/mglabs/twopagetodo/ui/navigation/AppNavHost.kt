package com.mglabs.twopagetodo.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    val snackBarHostState = remember { SnackbarHostState() }
    NavHost(navController = navController, startDestination = HomeScreenUIModel) {
        composable<HomeScreenUIModel> {
            HomeScreen(snackBarHostState = snackBarHostState, onNavigateToDetails = { todo: TodoTask ->
                navController.navigate(todo.transform())
            })
        }
        composable<DetailScreenUIModel> {
            DetailsScreen(snackBarHostState = snackBarHostState,onNavigateToHome = {
                navController.navigate(HomeScreenUIModel)
            })
        }
    }
}