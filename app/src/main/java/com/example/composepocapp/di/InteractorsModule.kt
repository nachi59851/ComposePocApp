package com.example.composepocapp.di

import com.example.composepocapp.cache.dao.RecipeDao
import com.example.composepocapp.cache.model.RecipeEntityMapper
import com.example.composepocapp.intractors.recipe.GetRecipe
import com.example.composepocapp.intractors.recipe_list.RestoreRecipes
import com.example.composepocapp.intractors.recipe_list.SearchRecipes
import com.example.composepocapp.network.RecipeService
import com.example.composepocapp.network.model.RecipeDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class) // Exist as long as view model
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideSearchRecipe(
        recipeService: RecipeService,
        recipeDao: RecipeDao,
        recipeEntityMapper: RecipeEntityMapper,
        recipeDtoMapper: RecipeDtoMapper
    ): SearchRecipes{
        return SearchRecipes(
            recipeService = recipeService,
            recipeDao = recipeDao,
            entityMapper = recipeEntityMapper,
            dtoMapper = recipeDtoMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideRestoreRecipes(
        recipeDao: RecipeDao,
        recipeEntityMapper: RecipeEntityMapper
    ):RestoreRecipes{
        return RestoreRecipes(
            recipeDao = recipeDao,
            entityMapper = recipeEntityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetRecipe(
        recipeDao: RecipeDao,
        recipeEntityMapper: RecipeEntityMapper,
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper,
    ): GetRecipe {
        return GetRecipe(
            recipeDao = recipeDao,
            entityMapper = recipeEntityMapper,
            recipeService = recipeService,
            recipeDtoMapper = recipeDtoMapper,
        )
    }
}