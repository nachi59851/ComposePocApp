package com.example.composepocapp.presentation.components.util

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController
constructor(
    private val scope: CoroutineScope
)
{
    private var snackBarJob: Job? = null

    init {
        cancleActiveJob()
    }

    fun getScope() = scope

    fun showSnackBar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String,
    ){
        if(snackBarJob == null){
            snackBarJob = scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message = message,actionLabel = actionLabel)
                cancleActiveJob()
            }
        }else{
                cancleActiveJob()
                snackBarJob = scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar( message = message, actionLabel = actionLabel)
                    cancleActiveJob()
                }
        }
    }

    private fun cancleActiveJob(){
        snackBarJob?.let {
            it.cancel()
            snackBarJob = Job()
        }
    }
}