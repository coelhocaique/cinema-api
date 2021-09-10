package com.coelhocaique.cinema.core.service.movie

import com.coelhocaique.cinema.core.service.movie.MovieMapper.toMovieResponse
import com.coelhocaique.cinema.core.service.movie.client.OmdbClient
import com.coelhocaique.cinema.core.persistance.MovieRepository
import com.coelhocaique.cinema.core.service.movie.MovieMapper.toMovieDocument
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.UUID

@Service
class MovieService(private val movieRepository: MovieRepository,
                   private val omdbClient: OmdbClient) {

    fun findById(id: UUID): Mono<MovieResponse> {
        return movieRepository.findById(id)
            .flatMap { omdbClient.retrieveMovieDetails(it.imdbId).zipWith(just(it)) }
            .flatMap { toMovieResponse(it.t2, it.t1) }
    }

    fun findAll(): Mono<List<MovieResponse>> {
        return movieRepository.findAll()
                    .flatMap { omdbClient.retrieveMovieDetails(it.imdbId).zipWith(just(it)) }
                    .flatMap { toMovieResponse(it.t2, it.t1) }
                    .collectList()
    }

    fun create(movieRequest: MovieRequest): Mono<MovieResponse> {
        return omdbClient.retrieveMovieDetails(movieRequest.imdbId!!)
            .flatMap { toMovieDocument(it).zipWith(just(it)) }
            .flatMap { movieRepository.save(it.t1).zipWith(just(it.t2)) }
            .flatMap { toMovieResponse(it.t1, it.t2) }
    }

}