package com.coelhocaique.cinema.core.domain.movie

import com.coelhocaique.cinema.core.domain.movie.client.OmdbResponse
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