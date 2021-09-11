package com.coelhocaique.cinema.core.persistance

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

interface MovieSessionRepository : ReactiveMongoRepository<MovieSessionDocument, UUID> {

    fun findByMovieIdAndIdAndActive(movieId: UUID, id: UUID, active: Boolean): Mono<MovieSessionDocument>

    fun findByMovieIdAndActive(movieId: UUID, active: Boolean): Flux<MovieSessionDocument>

    fun findByMovieIdAndDateAndTimeGreaterThanEqualAndActive(movieId: UUID, date: LocalDate, time: LocalTime, active: Boolean): Flux<MovieSessionDocument>

}