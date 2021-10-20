package com.example.composepocapp.domain.model

import java.util.*


data class NewRecipe(

    val newId: Int,
    val newTitle: String,
    val newPublisher: String,
    val newFeaturedImage: String,
    val newRating: Int,
    val newSourceUrl: String,
    val newIngredients: List<String> = listOf(),
    val newDateAdded: Date,
    val newDateUpdate: Date
)