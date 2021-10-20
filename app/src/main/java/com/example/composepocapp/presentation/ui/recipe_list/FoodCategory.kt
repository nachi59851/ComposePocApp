package com.example.composepocapp.presentation.ui.recipe_list

import com.example.composepocapp.presentation.ui.recipe_list.FoodCategory.*

enum class FoodCategory(val value: String) {

    CHICKEN("Chicken"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    //MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),

}

fun getFoodCategories(): List<FoodCategory>{
    return listOf(CHICKEN, SOUP, DESSERT, VEGETARIAN, VEGAN, PIZZA, DONUT)
}

fun getFoodCategory(value: String): FoodCategory?{
    val map = FoodCategory.values().associateBy (FoodCategory::value)
    return map[value]
}