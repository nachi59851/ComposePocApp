package com.example.composepocapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.composepocapp.R
import com.example.composepocapp.presentation.BaseApplication
import com.example.composepocapp.presentation.components.RecipeList
import com.example.composepocapp.presentation.components.SearchAppBar
import com.example.composepocapp.presentation.components.util.SnackbarController
import com.example.composepocapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalComposeUiApi
@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    private val snackbarController = SnackbarController(lifecycleScope)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value

                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value

                val loading = viewModel.loading.value

                val page = viewModel.page.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    darkTheme = application.isDark.value ,
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                ) {
                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                                      /*if(viewModel.selectedCategory.value?.value == "Milk"){
                                                          snackbarController.getScope().launch {
                                                              snackbarController.showSnackBar(
                                                                  scaffoldState = scaffoldState,
                                                                  message = "Invalid category: MILK",
                                                                  actionLabel = "Hide"
                                                              )
                                                          }
                                                      }else{
                                                          viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                                      }*/
                                                    viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                                  },
                                categories = getFoodCategories(),
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onToggleTheme = application::toggleLightTheme
                            )
                        },
                        scaffoldState = scaffoldState,
                        //comment it later to check the use
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            onChangedScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onTriggerNextPage = {
                                viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
                            },
                            onNavigateToRecipeDetailScreen = {
                                val bundle = Bundle()
                                bundle.putInt("recipeId",it)
                                findNavController().navigate(R.id.viewRecipe,bundle)
                            }
                        )
                    }
                }
            }
        }
    }
}