package com.coelhocaique.cinema.core.util.exception

import java.util.UUID

data class CoreException(
    val errorMessage: String? = null,
    val ex: Throwable? = null
) : RuntimeException(ex) {

    companion object CoreExceptionHelper {
        fun movieSessionNotFound(id: UUID) = CoreException("Movie session $id not found!")
        fun movieNotFound(id: UUID) = CoreException("Movie $id not found!")
        fun imdbNotFound(imdbId: String) = CoreException("IMDB $imdbId not found!")
        fun imdbAlreadyExists(imdbId: String) = CoreException("IMDB $imdbId already exists!")
    }
}