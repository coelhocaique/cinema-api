package com.coelhocaique.cinema.core.service.movie.client

data class OmdbResponse(
    val title: String,
    val imdbRating: Double,
    val released: String,
    val runtime: String,
    val ratings: List<OmdbRatingsResponse>? = null
)

data class OmdbRatingsResponse(
    val source: String,
    val value: String
)