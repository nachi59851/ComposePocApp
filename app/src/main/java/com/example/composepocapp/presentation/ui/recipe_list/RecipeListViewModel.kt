package com.example.composepocapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composepocapp.domain.model.Recipe
import com.example.composepocapp.intractors.recipe_list.RestoreRecipes
import com.example.composepocapp.intractors.recipe_list.SearchRecipes
import com.example.composepocapp.repository.RecipeRepository
import com.example.composepocapp.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class RecipeListViewModel
@Inject constructor(
    private val searchRecipes: SearchRecipes,
    private val restoreRecipes: RestoreRecipes,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle // just like shared preferences
): ViewModel()
{

    val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    var recipeListScrollPosition = 0

    //Pagination start at 1 (-1 = exhausted)
    var page = mutableStateOf(1)


    init {

        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let {
            setPage(it)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let{
            setQuery(it)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let{
            setListScrollPosition(it)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let {
            setSelectedCategory(it)
        }

        if(recipeListScrollPosition != 0 ){
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        }else{
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }
    }

    fun onTriggerEvent(recipeListEvent: RecipeListEvent){

        viewModelScope.launch {
            try{

                when(recipeListEvent){

                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }

                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }

                    is RecipeListEvent.RestoreStateEvent -> {
                        restoreState()
                    }

                }

            }catch (exception: Exception){
                Log.e(TAG,"launch job: Exception: ${exception}, ${exception.cause}")
                exception.printStackTrace()
            }finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    //calling from coroutine scope
    private fun restoreState(){

        restoreRecipes.execute(
            page = page.value,
            query = query.value
        ).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
                recipes.value = list
            }

            dataState.error?.let { error ->
                Log.e(TAG, "restoreState: $error")
            }
        }.launchIn(viewModelScope)

       /* loading.value = true

        val results: MutableList<Recipe> = mutableListOf()

        for(m in 1..page.value){
            //Server API call
            val result = repository.search(token = token,page = m,query = query.value)
            results.addAll(result)
            if(m == page.value){
                recipes.value = results
                loading.value = false
            }
        }*/
    }

    //calling from coroutine scope
    private fun nextPage(){

        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {

            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            if (page.value > 1) {
                searchRecipes.execute(
                    token = token,
                    page = page.value,
                    query = query.value
                ).onEach { dataState ->
                    loading.value = dataState.loading

                    dataState.data?.let { list ->
                        appendRecipes(list)
                    }

                    dataState.error?.let { error ->
                        Log.e(TAG, "nextPage: $error")
                    }
                }.launchIn(viewModelScope)
            }
        }

        /*if((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)){

            loading.value = true
            incrementPage()
            Log.d(TAG,"nextPage: triggered: ${page.value}")

            // to show Pagination API is fast
            delay(1000)

            if(page.value > 1){
                //Server API call
                val result = repository.search(token = token,page = page.value,query = query.value)
                Log.d(TAG,"search: Appending")
                appendRecipes(result)
            }
            loading.value = false
        }*/
    }

    //calling from coroutine scope
    private fun newSearch(){

        Log.d(TAG, "newSearch: query: ${query.value}, page: ${page.value}")

        resetSearchState()

        searchRecipes.execute(
            token = token,
            page = page.value,
            query = query.value
        ).onEach { dataState ->

            loading.value = dataState.loading

            dataState.data?.let { list ->
                recipes.value = list
            }

            dataState.error?.let { error ->
                Log.e(TAG, "newSearch: $error")
            }
        }.launchIn(viewModelScope) // clear when viewModel gets destroyed. So this job will get die


        /*loading.value = true

        resetSearchState()

        delay(2000)

        //Server API call
        val result = repository.search(token = token,page = 1,query = query.value)

        this.recipes.value = result

        loading.value = false*/
    }

    private fun appendRecipes(tempRecipes : List<Recipe>){

        val current = ArrayList(this.recipes.value)
        current.addAll(tempRecipes)
        this.recipes.value = current
    }

    private fun resetSearchState(){
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if(selectedCategory.value?.value != query.value){
            clearSelectedCategory()
        }
    }

    private fun incrementPage(){
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int){
        setListScrollPosition(position = position)
    }

    private fun setListScrollPosition(position: Int){
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun clearSelectedCategory(){
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    fun onQueryChanged(query: String){
        setQuery(query = query)
    }

    private fun setSelectedCategory(category: FoodCategory?){
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY,category)
    }

    private fun setQuery(query: String){
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY,query)
    }
}