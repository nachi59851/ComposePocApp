package com.example.composepocapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import com.example.composepocapp.presentation.components.SearchAppBar
import com.example.composepocapp.presentation.theme.AppTheme
import com.example.composepocapp.utils.TAG

@Composable
fun RecipeListScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onToggleTheme: () -> Unit,
    onNavigationToRecipeDetailScreen: (String) -> Unit,
    viewModel: RecipeListViewModel
){

    /*Log.d(TAG, "RecipeListScreen: $viewModel")

    val recipes = viewModel.recipes.value

    val query = viewModel.query.value

    val selectedCategory = viewModel.selectedCategory.value

    val loading = viewModel.loading.value

    val page = viewModel.page.value

    val scaffoldState = rememberScaffoldState()


    AppTheme(
        darkTheme = isDarkTheme,
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
        isNetworkAvailable = isNetworkAvailable,
    ) {
        Scaffold (
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChanged = viewModel::onQueryChanged,
                    onExecuteSearch = {
                        viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                    },
                    categories = getFoodCategories(),
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged =viewModel::onSelectedCategoryChanged,
                    onToggleTheme = {
                        onToggleTheme()
                    }
                ) {

                }
            }
        ){

        }
    }*/
}