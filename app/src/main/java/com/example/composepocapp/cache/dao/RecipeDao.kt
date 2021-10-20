package com.example.composepocapp.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composepocapp.cache.model.RecipeEntity
import com.example.composepocapp.domain.model.Recipe
import com.example.composepocapp.utils.RECIPE_PAGINATION_PAGE_SIZE

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertRecipe(recipeEntity: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>): LongArray

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity?

    @Query("DELETE FROM recipes WHERE id IN (:idList)")
    suspend fun deleteRecipes(idList: List<Int>): Int

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

    @Query("DELETE FROM recipes WHERE id = :primaryKey")
    suspend fun deleteRecipe(primaryKey: Int): Int


    @Query("SELECT * FROM recipes ORDER BY date_updated DESC LIMIT :pageSize OFFSET ((:page-1) * :pageSize)")
    suspend fun getAllRecipes(
        page: Int,
        pageSize: Int = RECIPE_PAGINATION_PAGE_SIZE
    ): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%' OR ingredients LIKE '%' || :query || '%' ORDER BY date_updated DESC LIMIT :pageSize OFFSET ((:page-1) * :pageSize)")
    suspend fun searchRecipes(
        query: String,
        page: Int,
        pageSize: Int = RECIPE_PAGINATION_PAGE_SIZE
    ): List<RecipeEntity>

    @Query("SELECT * FROM recipes ORDER BY date_updated DESC LIMIT (:page * :pageSize)")
    suspend fun restoreAllRecipes(
        page: Int,
        pageSize: Int = RECIPE_PAGINATION_PAGE_SIZE
    ):List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%' OR ingredients LIKE '%' || :query || '%' ORDER BY date_updated DESC LIMIT (:page * :pageSize)")
    suspend fun restoreRecipes(
        query: String,
        page: Int,
        pageSize: Int = RECIPE_PAGINATION_PAGE_SIZE
    ): List<RecipeEntity>
}