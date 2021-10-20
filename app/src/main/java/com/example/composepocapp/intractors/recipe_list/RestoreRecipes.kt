package com.example.composepocapp.intractors.recipe_list

import com.example.composepocapp.cache.dao.RecipeDao
import com.example.composepocapp.cache.model.RecipeEntityMapper
import com.example.composepocapp.domain.model.Recipe
import com.example.composepocapp.domain.state.DataState
import com.example.composepocapp.utils.RECIPE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//Restore list of recipes
class RestoreRecipes(
    private val recipeDao: RecipeDao,
    private val entityMapper: RecipeEntityMapper
){

    fun execute(
        page: Int,
        query: String
    ): Flow<DataState<List<Recipe>>> = flow {

        try{
            emit(DataState.loading())

            // just to show pagination, api is fast
            delay(1000)

            val cacheResult = if (query.isBlank()){
                recipeDao.restoreAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }
            else{
                recipeDao.restoreRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }

            // emit List<Recipe> from cache
            val list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))
        }catch (e: Exception){
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }
}