package com.coelhocaique.cinema.core.service.session

import com.coelhocaique.cinema.core.persistance.MovieSessionDocument
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
            date = document.date,
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
            time = request.sessionDateTime!!.toLocalTime(),
            date = request.sessionDateTime.toLocalDate(),
            active = true,
            createdAt = LocalDateTime.now()
        )
    )
}