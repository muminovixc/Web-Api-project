package com.np.webapiapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.np.webapiapp.ui.screens.DatasetDetailScreen
import com.np.webapiapp.ui.screens.DatasetListScreen

sealed class Screen(val route: String) {
    object DatasetList : Screen("datasetList")
    object DatasetDetail : Screen("datasetDetail/{datasetId}") {
        fun createRoute(datasetId: String) = "datasetDetail/$datasetId"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.DatasetList.route
    ) {
        composable(Screen.DatasetList.route) {
            DatasetListScreen(
                onDatasetClick = { datasetId ->
                    navController.navigate(Screen.DatasetDetail.createRoute(datasetId))
                }
            )
        }

        composable(
            route = Screen.DatasetDetail.route,
            arguments = listOf(
                navArgument("datasetId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val datasetId = backStackEntry.arguments?.getString("datasetId") ?: return@composable
            DatasetDetailScreen(
                datasetId = datasetId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 