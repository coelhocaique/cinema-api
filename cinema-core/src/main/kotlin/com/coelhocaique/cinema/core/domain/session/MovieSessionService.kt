package com.coelhocaique.cinema.core.domain.session

import com.coelhocaique.cinema.core.domain.movie.MovieService
import com.coelhocaique.cinema.core.domain.session.MovieSessionMapper.toMovieSessionDocument
import com.coelhocaique.cinema.core.domain.session.MovieSessionMapper.toMovieSessionResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class MovieSessionService(
    private val movieSessionRepository: MovieSessionRepository,
    private val movieService: MovieService
) {
    fun findByMovieId(movieId: UUID): Mono<List<MovieSessionResponse>> {
        return movieSessionRepository.findByMovieAndActive(movieId, true)
                    .flatMap { toMovieSessionResponse(it) }
                    .collectList()
    }

    fun create(movieId: UUID, request: MovieSessionRequest): Mono<MovieSessionResponse> {
        return movieService.findById(movieId)
            .flatMap { toMovieSessionDocument(it.id, request) }
            .flatMap { movieSessionRepository.save(it) }
            .flatMap { toMovieSessionResponse(it) }
    }

    fun delete(movieId: UUID, id: UUID): Mono<MovieSessionResponse> {
        return movieSessionRepository.findByMovieIdAndIdAndActive(movieId, id, true)
            .flatMap { movieSessionRepository.save(it.copy(active = false)) }
            .flatMap { toMovieSessionResponse(it) }
    }
}