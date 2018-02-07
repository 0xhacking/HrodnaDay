package com.github.kiolk.hrodnaday

interface ResultCallback < in T> {
    fun onSuccess(param : T )

    fun onError(exception: Exception)
}

