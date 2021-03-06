package com.coelhocaique.cinema.core.service.movie.client

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(value = PropertyNamingStrategy.UpperCamelCaseStrategy::class)
data class OmdbResponse(
    @JsonProperty("imdbID") val imdbId: String,
    val title: String,
    @JsonProperty("imdbRating") val imdbRating: Double,
    val released: String,
    val runtime: String,
    val ratings: List<OmdbRatingsResponse>? = null
)

@JsonNaming(value = PropertyNamingStrategy.UpperCamelCaseStrategy::class)
data class OmdbRatingsResponse(
    val source: String,
    val value: String
)
