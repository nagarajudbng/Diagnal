package com.codelabs.diagnalprogrammingtest.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codelabs.diagnalprogrammingtest.feature_movies.presentation.MovieScreen
import com.codelabs.diagnalprogrammingtest.feature_movies.presentation.MovieViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.diagnalprogrammingtest.feature_search.presentation.SearchScreen
import com.codelabs.diagnalprogrammingtest.feature_search.presentation.SearchViewModel


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun AppNavHost(
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.HOME.route
    ) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val movieViewModel: MovieViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Column {
            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = startDestination
            ) {

                composable(NavigationItem.HOME.route) {
                    MovieScreen(
                        navController,
                        viewModel = movieViewModel
                    )
                }
                composable(NavigationItem.SEARCH.route) {
                    SearchScreen(
                        navController,
                        viewModel = searchViewModel
                    )
                }
            }
        }
    }

}