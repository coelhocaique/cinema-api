package com.coelhocaique.cinema.core.service.movie

import com.coelhocaique.cinema.core.persistance.MovieDocument
import com.coelhocaique.cinema.core.service.movie.MovieMapper.toMovieResponse
import com.coelhocaique.cinema.core.service.movie.client.OmdbClient
import com.coelhocaique.cinema.core.persistance.MovieRepository
import com.coelhocaique.cinema.core.service.movie.MovieMapper.toMovieDocument
import com.coelhocaique.cinema.core.service.movie.client.OmdbResponse
import com.coelhocaique.cinema.core.util.exception.CoreException.CoreExceptionHelper.imdbAlreadyExists
import com.coelhocaique.cinema.core.util.exception.CoreException.CoreExceptionHelper.movieNotFound
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import reactor.util.function.Tuple2
import java.util.UUID

@Service
class MovieService(private val movieRepository: MovieRepository,
                   private val omdbClient: OmdbClient) {

    fun findById(id: UUID): Mono<MovieResponse> {
        return movieRepository.findById(id)
            .switchIfEmpty(Mono.error { movieNotFound(id) })
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
        return validate(movieRequest)
            .flatMap { toMovieDocument(it.t2).zipWith(just(it.t2)) }
            .flatMap { movieRepository.insert(it.t1).zipWith(just(it.t2)) }
            .flatMap { toMovieResponse(it.t1, it.t2) }
    }

    private fun validate(movieRequest: MovieRequest): Mono<Tuple2<MovieDocument, OmdbResponse>> =
        omdbClient.retrieveMovieDetails(movieRequest.imdbId!!)
            .flatMap { movieRepository.findByImdbId(it.imdbId).zipWith(just(it)) }
            .doOnNext { movie ->
                if (movie != null) {
                    throw imdbAlreadyExists(movie.t1.imdbId)
                }
            }
}