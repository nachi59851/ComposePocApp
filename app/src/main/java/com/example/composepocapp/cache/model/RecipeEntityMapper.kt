package com.example.composepocapp.cache.model

import com.example.composepocapp.domain.model.Recipe
import com.example.composepocapp.domain.util.DomainMapper
import com.example.composepocapp.utils.DateUtils
import java.lang.StringBuilder

class RecipeEntityMapper: DomainMapper<RecipeEntity, Recipe> {

    // From Server
    override fun mapToDomainModel(model: RecipeEntity): Recipe {
        return Recipe(
            id = model.id,
            title = model.title,
            featuredImage = model.featuredImage,
            rating = model.rating,
            publisher = model.publisher,
            sourceUrl = model.sourceUrl,
            ingredients = convertIngredientsToList(model.ingredients),
            newDateAdded = DateUtils.longToDate(model.dateAdded),
            newDateUpdate = DateUtils.longToDate(model.dateUpdated),
        )
    }

    //To server
    override fun mapFromDomainModel(domainModel: Recipe): RecipeEntity {
        return RecipeEntity(
            id = domainModel.id,
            title = domainModel.title,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            publisher = domainModel.publisher,
            sourceUrl = domainModel.sourceUrl,
            ingredients = convertIngredientListToString(domainModel.ingredients),
            dateAdded = DateUtils.dateToLong(domainModel.newDateAdded),
            dateUpdated = DateUtils.dateToLong(domainModel.newDateUpdate),
            dateCached = DateUtils.dateToLong(DateUtils.createTimestamp())
        )
    }

    //Comma separated value
    private fun convertIngredientListToString(ingredients: List<String>): String{
        val ingredientsString = StringBuilder()
        for(ingredient in ingredients){
            ingredientsString.append("$ingredient,")
        }
        return ingredientsString.toString()
    }

    private fun convertIngredientsToList(ingredientsString: String?): List<String>{
        val list: ArrayList<String> = ArrayList()
        ingredientsString?.let {
            for(ingredient in it.split(",")){
                list.add(ingredient)
            }
        }
        return list
    }

    fun fromEntityList(initial: List<RecipeEntity>): List<Recipe>{
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}