package com.example.composepocapp.presentation.ui.recipe

sealed class RecipeEvent{

    data class GetRecipeEvent(
        val id: Int
    ):RecipeEvent()
}
