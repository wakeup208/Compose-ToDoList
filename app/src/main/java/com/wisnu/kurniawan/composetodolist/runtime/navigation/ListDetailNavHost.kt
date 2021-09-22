package com.wisnu.kurniawan.composetodolist.runtime.navigation

import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.wisnu.kurniawan.composetodolist.features.todo.detail.ui.CreateListEditor
import com.wisnu.kurniawan.composetodolist.features.todo.detail.ui.ListDetailScreen
import com.wisnu.kurniawan.composetodolist.features.todo.detail.ui.ListDetailViewModel
import com.wisnu.kurniawan.composetodolist.features.todo.detail.ui.TaskEditor
import com.wisnu.kurniawan.composetodolist.features.todo.detail.ui.UpdateListEditor
import com.wisnu.kurniawan.composetodolist.foundation.uiextension.bottomSheet
import com.wisnu.kurniawan.composetodolist.runtime.MainBottomSheetConfig
import com.wisnu.kurniawan.composetodolist.runtime.defaultMainBottomSheetConfig
import com.wisnu.kurniawan.composetodolist.runtime.noScrimSmallShapeMainBottomSheetConfig


@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.ListDetailNavHost(
    navController: NavHostController,
    bottomSheetConfig: MutableState<MainBottomSheetConfig>
) {
    // navController.navigate to ListDetailFlow.Root.route will crash due to not found
    // add "?$ARG_LIST_ID={$ARG_LIST_ID}" in startDestination for workaround
    navigation(
        startDestination = ListDetailFlow.ListDetailScreen.route + "?$ARG_LIST_ID={$ARG_LIST_ID}",
        route = ListDetailFlow.Root.route + "?$ARG_LIST_ID={$ARG_LIST_ID}"
    ) {
        composable(
            route = ListDetailFlow.ListDetailScreen.route + "?$ARG_LIST_ID={$ARG_LIST_ID}",
            arguments = listOf(
                navArgument(ARG_LIST_ID) {
                    defaultValue = ""
                }
            )
        ) {
            val viewModel = hiltViewModel<ListDetailViewModel>()
            ListDetailScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        bottomSheet(ListDetailFlow.CreateList.route) {
            val viewModel = if (navController.previousBackStackEntry != null) {
                hiltViewModel<ListDetailViewModel>(
                    navController.previousBackStackEntry!!
                )
            } else {
                hiltViewModel()
            }
            bottomSheetConfig.value = defaultMainBottomSheetConfig
            CreateListEditor(
                viewModel = viewModel,
                navController = navController
            )
        }
        bottomSheet(ListDetailFlow.UpdateList.route) {
            val viewModel = if (navController.previousBackStackEntry != null) {
                hiltViewModel<ListDetailViewModel>(
                    navController.previousBackStackEntry!!
                )
            } else {
                hiltViewModel()
            }
            bottomSheetConfig.value = defaultMainBottomSheetConfig
            UpdateListEditor(
                viewModel = viewModel,
                navController = navController
            )
        }
        bottomSheet(ListDetailFlow.CreateTask.route) {
            val viewModel = if (navController.previousBackStackEntry != null) {
                hiltViewModel<ListDetailViewModel>(
                    navController.previousBackStackEntry!!
                )
            } else {
                hiltViewModel()
            }
            bottomSheetConfig.value = noScrimSmallShapeMainBottomSheetConfig
            TaskEditor(
                viewModel = viewModel
            )
        }
    }
}
