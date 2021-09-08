package com.coelhocaique.cinema.core.domain.movie

import java.util.UUID

data class MovieResponse(
    val id: UUID,
    val title: String,
    val imdbRating: Double,
    val released: String,
    val runtime: String,
    val ratings: List<String>
)