package com.coelhocaique.cinema.core.service.movie

import com.coelhocaique.cinema.core.service.movie.client.OmdbResponse
import com.coelhocaique.cinema.core.persistance.MovieDocument
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.UUID

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

    fun toMovieDocument(
        omdbResponse: OmdbResponse
    ) = just(
        MovieDocument(
            id = UUID.randomUUID(),
            imdbId = omdbResponse.imdbId,
            createdAt = LocalDateTime.now()
        )
    )
}