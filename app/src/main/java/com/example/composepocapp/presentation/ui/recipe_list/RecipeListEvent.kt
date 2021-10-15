package com.example.composepocapp.presentation.ui.recipe_list

sealed class RecipeListEvent{

    object NextPageEvent: RecipeListEvent()

    object NewSearchEvent: RecipeListEvent()

    object RestoreStateEvent: RecipeListEvent()
}
