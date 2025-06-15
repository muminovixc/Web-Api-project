package com.np.webapiapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.np.webapiapp.data.model.Dataset
import com.np.webapiapp.ui.screens.DatasetDetailScreen
import com.np.webapiapp.ui.screens.DatasetListScreen
import com.np.webapiapp.ui.viewmodel.DatasetViewModel
import com.google.gson.Gson

sealed class Screen(val route: String) {
    object DatasetList : Screen("datasetList")
    object DatasetDetail : Screen("datasetDetail/{dataset}") {
        fun createRoute(dataset: Dataset) = "datasetDetail/${Gson().toJson(dataset)}"
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: DatasetViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DatasetList.route
    ) {
        composable(Screen.DatasetList.route) {
            DatasetListScreen(
                viewModel = viewModel,
                onDatasetClick = { dataset ->
                    navController.navigate(Screen.DatasetDetail.createRoute(dataset))
                }
            )
        }

        composable(
            route = Screen.DatasetDetail.route,
            arguments = listOf(
                navArgument("dataset") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val datasetJson = backStackEntry.arguments?.getString("dataset")
            val dataset = remember(datasetJson) {
                Gson().fromJson(datasetJson, Dataset::class.java)
            }
            DatasetDetailScreen(
                dataset = dataset,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 