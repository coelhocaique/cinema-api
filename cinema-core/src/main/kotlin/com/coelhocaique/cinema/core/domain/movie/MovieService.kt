package com.coelhocaique.cinema.core.domain.movie

import com.coelhocaique.cinema.core.domain.movie.MovieMapper.toMovieResponse
import com.coelhocaique.cinema.core.domain.movie.client.OmdbClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.UUID

@Service
class MovieService(private val repository: MovieRepository,
                   private val omdbClient: OmdbClient) {

    fun findById(id: UUID): Mono<MovieResponse> {
        return repository.findById(id)
            .flatMap { omdbClient.retrieveMovieDetails(it.imdbId).zipWith(just(it)) }
            .flatMap { toMovieResponse(it.t2, it.t1) }
    }

    fun findAll(): Mono<List<MovieResponse>> {
        return repository.findAll()
                    .flatMap { omdbClient.retrieveMovieDetails(it.imdbId).zipWith(just(it)) }
                    .flatMap { toMovieResponse(it.t2, it.t1) }
                    .collectList()
    }

}