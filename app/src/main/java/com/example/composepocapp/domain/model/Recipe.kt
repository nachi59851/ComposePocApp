package com.example.composepocapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val publisher: String,
    val featuredImage: String,
    val rating: Int = 0,
    val sourceUrl: String,
    //val description: String,
    //val cookingInstructions : String,
    val ingredients: List<String> = listOf(),
    //val dateAdded: String,
    //val dateUpdated: String,
    val newDateAdded: Date,
    val newDateUpdate: Date

) : Parcelable{


}