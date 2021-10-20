package com.example.composepocapp.presentation.ui.recipe

import androidx.compose.runtime.Composable

@Composable
fun RecipeDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    recipeId: Int?,
    viewModel: RecipeViewModel,
){
    if(recipeId == null){
        // Show Invalid Id
    }else{
        // Get the recipe from serer
        val onLoad = viewModel.loading.value

    }
}