package com.coelhocaique.cinema.core.persistance

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.UUID

interface MovieRepository: ReactiveMongoRepository<MovieDocument, UUID> {

    fun findByImdbId(imdbId: String): Mono<MovieDocument>
}