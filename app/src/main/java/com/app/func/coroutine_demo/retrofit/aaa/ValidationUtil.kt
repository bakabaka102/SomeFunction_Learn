package com.app.func.coroutine_demo.retrofit.aaa

object ValidationUtil {

    fun isValidateMovies(movie: Movie) : Boolean {
        return movie.name.isNotEmpty() && movie.category.isNotEmpty()
    }
}