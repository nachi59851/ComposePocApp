package com.example.composepocapp.domain.state

// List<out T> is like List<? extends T> in Java
data class DataState<out T> (
    val data: T? = null,
    val error: String? = null,
    val loading: Boolean = false
){
    companion object{

        fun <T> success(
            data: T
        ): DataState<T>{
            return DataState(
                data = data
            )
        }

        fun error(
            message: String
        ):DataState<Nothing>{
            return DataState(
                error = message
            )
        }

        fun loading(): DataState<Nothing> = DataState(loading = true)

    }
}