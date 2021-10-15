package com.example.composepocapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composepocapp.domain.model.Recipe
import com.example.composepocapp.repository.RecipeRepository
import com.example.composepocapp.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "recipe.state.recipe.key"

@HiltViewModel
class RecipeViewModel
    @Inject constructor(
        private val repository: RecipeRepository,
        @Named("auth_token") private val token: String,
        private val state: SavedStateHandle
    ): ViewModel() {

        val recipe: MutableState<Recipe?> = mutableStateOf(null)
        val loading = mutableStateOf(false)

        init {
            //Restore data
            state.get<Int>(STATE_KEY_RECIPE)?.let {
                onTriggerEvent(RecipeEvent.GetRecipeEvent(it))
            }
        }

    fun onTriggerEvent(event: RecipeEvent){

        viewModelScope.launch {
            try{
                when(event){
                    is RecipeEvent.GetRecipeEvent -> {
                        if(recipe.value == null){
                            getRecipe(recipeId = event.id)
                        }
                    }
                }
            }catch (exception: Exception){
                Log.e(TAG, "launchJob: Exception: ${exception}, ${exception.cause}")
                exception.printStackTrace()
            }
        }
    }

    private suspend fun getRecipe(recipeId: Int){
        loading.value = true

        delay(1000)

        val recipe = repository.get(
            token = token,
            id = recipeId
        )

        this.recipe.value = recipe

        state.set(STATE_KEY_RECIPE,recipe.id)

        loading.value = false
    }
}