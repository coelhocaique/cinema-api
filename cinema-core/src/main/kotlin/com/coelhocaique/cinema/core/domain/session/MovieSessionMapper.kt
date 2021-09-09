package com.coelhocaique.cinema.core.domain.session

import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.UUID

object MovieSessionMapper {

    fun toMovieSessionResponse(
        document: MovieSessionDocument
    ) = just(
        MovieSessionResponse(
            id = document.id,
            movieId = document.movieId,
            price = document.price,
            room = document.room,
            time = document.time,
            createdAt = LocalDateTime.now()
        )
    )

    fun toMovieSessionDocument(
        movieId: UUID,
        request: MovieSessionRequest
    ) = just(
        MovieSessionDocument(
            id = UUID.randomUUID(),
            movieId = movieId,
            price = request.price!!,
            room = request.room,
            time = request.time!!,
            active = true,
            createdAt = LocalDateTime.now()
        )
    )
}