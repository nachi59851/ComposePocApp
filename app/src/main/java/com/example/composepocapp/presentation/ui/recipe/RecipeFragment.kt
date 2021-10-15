package com.example.composepocapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.composepocapp.presentation.BaseApplication
import com.example.composepocapp.presentation.components.*
import com.example.composepocapp.presentation.components.util.SnackbarController
import com.example.composepocapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()

    private val snackbarController = SnackbarController(lifecycleScope)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let {
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value
                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                    darkTheme = application.isDark.value
                ){

                    Scaffold (
                        scaffoldState =scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ){
                       Box(
                           modifier = Modifier.fillMaxSize()
                       ) {
                           if(loading && recipe == null) LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp)
                           else{
                               recipe?.let {
                                   if(it.id == 1){
                                       snackbarController.getScope().launch {
                                           snackbarController.showSnackBar(
                                               scaffoldState = scaffoldState,
                                               message = "An error occurred with this recipe",
                                               actionLabel = "Ok"
                                           )
                                       }
                                   }else{
                                       RecipeView(
                                           recipe = it
                                       )
                                   }
                               }
                               CircularIndeterminateProgressBar(
                                   isDisplayed = loading,
                                   verticalBias = 0.3f
                               )
                               DefaultSnackBar(
                                   snackbarHostState = scaffoldState.snackbarHostState,
                                   onDismiss = {
                                       scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                   },
                                   modifier = Modifier.align(Alignment.BottomCenter)
                               )
                           }
                       }
                    }
                }
            }
        }
    }
}