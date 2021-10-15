package com.example.composepocapp.di

import com.example.composepocapp.network.RecipeService
import com.example.composepocapp.network.model.RecipeDtoMapper
import com.example.composepocapp.repository.RecipeRepository
import com.example.composepocapp.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository{
        return RecipeRepository_Impl(
            recipeService = recipeService,
            mapper = recipeDtoMapper
        )
    }
}