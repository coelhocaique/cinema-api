package com.coelhocaique.cinema.core.domain.movie.client

data class OmdbResponse(val title: String,
                        val imdbRating: Double,
                        val released: String,
                        val runtime: String,
                        val ratings: List<String>)