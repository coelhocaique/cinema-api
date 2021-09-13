package com.coelhocaique.cinema.core.service.session

import com.coelhocaique.cinema.core.service.movie.MovieService
import com.coelhocaique.cinema.core.service.session.MovieSessionMapper.toMovieSessionDocument
import com.coelhocaique.cinema.core.service.session.MovieSessionMapper.toMovieSessionResponse
import com.coelhocaique.cinema.core.persistance.MovieSessionRepository
import com.coelhocaique.cinema.core.util.exception.CoreException.CoreExceptionHelper.movieSessionNotFound
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.UUID

@Service
class MovieSessionService(
    private val movieSessionRepository: MovieSessionRepository,
    private val movieService: MovieService
) {

    fun findByMovieId(movieId: UUID): Mono<List<MovieSessionResponse>> {
        return movieSessionRepository.findByMovieIdAndActive(movieId, true)
                    .flatMap { toMovieSessionResponse(it) }
                    .collectList()
    }

    fun findByMovieIdAndDateTime(movieId: UUID, dateTime: LocalDateTime): Mono<List<MovieSessionResponse>> {
        return movieSessionRepository.findByMovieIdAndDateAndTimeGreaterThanEqualAndActive(movieId, dateTime.toLocalDate(), dateTime.toLocalTime(), true)
            .flatMap { toMovieSessionResponse(it) }
            .collectList()
    }

    fun create(movieId: UUID, request: MovieSessionRequest): Mono<MovieSessionResponse> {
        return movieService.findById(movieId)
            .flatMap { toMovieSessionDocument(it.id, request) }
            .flatMap { movieSessionRepository.insert(it) }
            .flatMap { toMovieSessionResponse(it) }
    }

    fun delete(movieId: UUID, id: UUID): Mono<Void> {
        return movieSessionRepository.findByMovieIdAndIdAndActive(movieId, id, true)
            .switchIfEmpty(Mono.error { movieSessionNotFound(id) })
            .flatMap { movieSessionRepository.save(it.copy(active = false)) }
            .flatMap { Mono.empty<Void?>().then() }
    }
}