package com.coelhocaique.cinema.core.domain.movie

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class MovieService(private val repository: MovieRepository) {

    fun findById(id: UUID): Mono<MovieDocument> {
        return repository.findById(id)
    }

    fun findAll(): Mono<List<MovieDocument>> {
        return repository.findAll().collectList()
    }

}