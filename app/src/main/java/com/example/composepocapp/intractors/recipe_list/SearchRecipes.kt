package com.example.composepocapp.intractors.recipe_list

import com.example.composepocapp.cache.dao.RecipeDao
import com.example.composepocapp.cache.model.RecipeEntityMapper
import com.example.composepocapp.domain.model.Recipe
import com.example.composepocapp.domain.state.DataState
import com.example.composepocapp.network.RecipeService
import com.example.composepocapp.network.model.RecipeDtoMapper
import com.example.composepocapp.utils.RECIPE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipes(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService,
    private val entityMapper: RecipeEntityMapper,
    private val dtoMapper: RecipeDtoMapper
) {

    fun execute(
        token: String,
        page: Int,
        query: String
    ): Flow<DataState<List<Recipe>>> = flow {

        try {
            //Show loading
            emit(DataState.loading())

            if (query == "error") {
                throw Exception("Search FAILED!")
            }

            try {
                val recipes =  getRecipesFromNetwork(
                    token = token,
                    page = page,
                    query = query,
                )

                //insert data into DB
                recipeDao.insertRecipes(entityMapper.toEntityList(recipes))

            }catch (e: Exception){
                // Error while fetching data/ network issue
                e.printStackTrace()
            }

            // query the cache
            val cacheResult = if (query.isBlank()){
                //get all recipes
                recipeDao.getAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }else{
                recipeDao.searchRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }

            val list = entityMapper.fromEntityList(cacheResult)

            emit(DataState.success( list))

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "Unkown Error"))
        }
    }

    private suspend fun getRecipesFromNetwork(
        token: String,
        page: Int,
        query: String
    ): List<Recipe>{

        return dtoMapper.toDomainList(recipeService.search(
                token = token,
                page = page,
                query = query
            ).recipes
        )

    }
}