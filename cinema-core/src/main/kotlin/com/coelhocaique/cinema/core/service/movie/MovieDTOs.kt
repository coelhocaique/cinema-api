package com.coelhocaique.cinema.core.service.movie

import com.coelhocaique.cinema.core.service.movie.client.OmdbRatingsResponse
import java.util.UUID

data class MovieResponse(
    val id: UUID,
    val title: String,
    val imdbRating: Double,
    val released: String,
    val runtime: String,
    val ratings: List<OmdbRatingsResponse>?,
    val links: List<Map<String, String>>? = null
)