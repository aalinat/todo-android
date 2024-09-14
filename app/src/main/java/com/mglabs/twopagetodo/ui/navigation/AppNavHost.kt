package com.mglabs.twopagetodo.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.ui.presentation.details.DetailsScreen
import com.mglabs.twopagetodo.ui.presentation.details.DetailsScreenState
import com.mglabs.twopagetodo.ui.presentation.details.transform
import com.mglabs.twopagetodo.ui.presentation.home.HomeScreen
import com.mglabs.twopagetodo.ui.presentation.home.HomeScreenState

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    NavHost(navController = navController, startDestination = HomeScreenState) {
        composable<HomeScreenState> {
            HomeScreen(snackBarHostState, onNavigateToDetails = { todo: TodoTask ->
                navController.navigate(todo.transform())
            })
        }
        composable<DetailsScreenState> {
            DetailsScreen(snackBarHostState, onNavigateToHome = {
                navController.navigate(HomeScreenState)
            })
        }
    }
}