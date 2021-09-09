package com.coelhocaique.cinema.core.service.movie

import com.coelhocaique.cinema.core.service.movie.client.OmdbResponse
import com.coelhocaique.cinema.core.persistance.MovieDocument
import reactor.core.publisher.Mono.just

object MovieMapper {

    fun toMovieResponse(
        document: MovieDocument,
        omdbResponse: OmdbResponse
    ) = just(
        MovieResponse(
            id = document.id,
            title = omdbResponse.title,
            imdbRating = omdbResponse.imdbRating,
            released = omdbResponse.released,
            runtime = omdbResponse.runtime,
            ratings = omdbResponse.ratings
        )
    )
}