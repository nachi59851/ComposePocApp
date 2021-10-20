package com.example.composepocapp.intractors.recipe

import com.example.composepocapp.cache.dao.RecipeDao
import com.example.composepocapp.cache.model.RecipeEntityMapper
import com.example.composepocapp.domain.model.Recipe
import com.example.composepocapp.domain.state.DataState
import com.example.composepocapp.network.RecipeService
import com.example.composepocapp.network.model.RecipeDtoMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipe(
    private val recipeDao: RecipeDao,
    private val entityMapper: RecipeEntityMapper,
    private val recipeService: RecipeService,
    private val recipeDtoMapper: RecipeDtoMapper,
){

    fun execute(
        recipeId: Int,
        token: String,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<Recipe>> = flow {
        try{
            emit(DataState.loading())

            // just to show loading, cache is fast
            delay(1000)
            var recipe = getRecipeFromCache(recipeId = recipeId)

            if(recipe != null){
                emit(DataState.success(recipe))
            }else{
                // Get the recipe details from server
                if(isNetworkAvailable){

                    val networkRecipe = getRecipeFromNetwork(token, recipeId)

                    // insert into DB
                    recipeDao.insertRecipe(
                        entityMapper.mapFromDomainModel(networkRecipe)
                    )
                }

                // get from DB
                recipe = getRecipeFromCache(recipeId = recipeId)
                if(recipe != null){
                    emit(DataState.success(recipe))
                }
                else{
                    throw Exception("Unable to get recipe from the cache.")
                }
            }

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }


    private suspend fun getRecipeFromCache(recipeId: Int): Recipe? {
        return recipeDao.getRecipeById(recipeId)?.let { recipeEntity ->
            entityMapper.mapToDomainModel(recipeEntity)
        }
    }

    private suspend fun getRecipeFromNetwork(token: String, recipeId: Int): Recipe {
        return recipeDtoMapper.mapToDomainModel(recipeService.get(token, recipeId))
    }
}