package com.example.composepocapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composepocapp.cache.dao.RecipeDao
import com.example.composepocapp.cache.model.RecipeEntity

@Database(entities = [RecipeEntity::class],version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun recipeDao(): RecipeDao

    companion object{
        val DATABASE_NAME: String = "recipe_db"
    }
}