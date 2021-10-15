package com.example.composepocapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composepocapp.domain.model.Recipe
import com.example.composepocapp.presentation.ui.recipe_list.PAGE_SIZE

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangedScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onNavigateToRecipeDetailScreen: (Int) -> Unit,
){
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ){
        if(loading && recipes.isEmpty()){
            HorizontalDottedProgressBar()
        }else if (recipes.isEmpty()){
            //No Data Found
            NoDataFound()
        }else{
            //Show the list
            LazyColumn{
                itemsIndexed(
                    items = recipes
                ){
                    index, item ->
                    onChangedScrollPosition(index)
                    if((index+1) >= (page * PAGE_SIZE) && !loading){
                        onTriggerNextPage()
                    }
                    RecipeCard(
                        recipe = item,
                        onClick = {
                            item.id?.let { onNavigateToRecipeDetailScreen(it) }
                        }
                    )
                }
            }
        }
    }

}
