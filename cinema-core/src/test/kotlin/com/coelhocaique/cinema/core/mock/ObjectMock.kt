package com.coelhocaique.cinema.core.mock

import com.coelhocaique.cinema.core.service.movie.MovieResponse
import java.util.UUID

fun mockMovieResponse() = MovieResponse(
    id = UUID.randomUUID(),
    title = "The Fast and the Furious",
    imdbRating = 6.8,
    released = "22 Jun 2001",
    runtime = "106 min",
    ratings = emptyList()
)