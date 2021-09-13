package com.coelhocaique.cinema.core.service

import com.coelhocaique.cinema.core.mock.mockMovieResponse
import com.coelhocaique.cinema.core.mock.mockMovieSessionDocument
import com.coelhocaique.cinema.core.mock.mockMovieSessionRequest
import com.coelhocaique.cinema.core.persistance.MovieSessionDocument
import com.coelhocaique.cinema.core.persistance.MovieSessionRepository
import com.coelhocaique.cinema.core.service.movie.MovieService
import com.coelhocaique.cinema.core.service.session.MovieSessionService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono.just
import reactor.test.StepVerifier
import java.time.LocalDateTime

class MovieSessionServiceTest {

    @Test
    fun testFindByMovieId() {
        val movieService = mockk<MovieService>()
        val repository = mockk<MovieSessionRepository>()
        val service = MovieSessionService(repository, movieService)
        val document = mockMovieSessionDocument()
        val movieId = document.movieId

        every { repository.findByMovieIdAndActive(movieId, true) } answers { Flux.just(document) }

        StepVerifier.create(service.findByMovieId(movieId))
            .assertNext {
                assertEquals(1, it.size)
                assertEquals(document.id, it[0].id)
                assertEquals(document.movieId, it[0].movieId)
                assertEquals(document.price, it[0].price)
                assertEquals(document.date, it[0].date)
                assertEquals(document.time, it[0].time)
                assertEquals(document.room, it[0].room)
            }
            .verifyComplete()


        verify(exactly = 1) { repository.findByMovieIdAndActive(movieId, true) }
    }

    @Test
    fun testFindByMovieIdAndDateTime() {
        val movieService = mockk<MovieService>()
        val repository = mockk<MovieSessionRepository>()
        val service = MovieSessionService(repository, movieService)
        val document = mockMovieSessionDocument()
        val datetime = LocalDateTime.of(2021, 9, 9, 18, 1)
        val movieId = document.movieId

        every {
            repository.findByMovieIdAndDateAndTimeGreaterThanEqualAndActive(movieId, datetime.toLocalDate(), datetime.toLocalTime(), true)
        } answers { Flux.just(document) }

        StepVerifier.create(service.findByMovieIdAndDateTime(movieId, datetime))
            .assertNext {
                assertEquals(1, it.size)
                assertEquals(document.id, it[0].id)
                assertEquals(document.movieId, it[0].movieId)
                assertEquals(document.price, it[0].price)
                assertEquals(document.date, it[0].date)
                assertEquals(document.time, it[0].time)
                assertEquals(document.room, it[0].room)
            }
            .verifyComplete()


        verify(exactly = 1) {
            repository.findByMovieIdAndDateAndTimeGreaterThanEqualAndActive(movieId, datetime.toLocalDate(), datetime.toLocalTime(), true)
        }
    }

    @Test
    fun create() {
        val movieService = mockk<MovieService>()
        val repository = mockk<MovieSessionRepository>()
        val service = MovieSessionService(repository, movieService)
        val request = mockMovieSessionRequest()
        val movieResponse = mockMovieResponse()
        val movieId = movieResponse.id

        every { movieService.findById(movieId) } answers { just(movieResponse) }
        every { repository.insert(any() as MovieSessionDocument) } answers { just(it.invocation.args[0] as MovieSessionDocument) }

        StepVerifier.create(service.create(movieId, request))
            .assertNext {
                assertNotNull(it.id)
                assertEquals(movieId, it.movieId)
                assertEquals(request.price, it.price)
                assertEquals(request.sessionDateTime!!.toLocalDate(), it.date)
                assertEquals(request.sessionDateTime!!.toLocalTime(), it.time)
                assertEquals(request.room, it.room)
            }
            .verifyComplete()


        verify(exactly = 1) {
            repository.insert(any() as MovieSessionDocument)
            movieService.findById(movieId)
        }
    }

    @Test
    fun delete() {
        val movieService = mockk<MovieService>()
        val repository = mockk<MovieSessionRepository>()
        val service = MovieSessionService(repository, movieService)
        val document = mockMovieSessionDocument()
        val movieId = document.movieId
        val id = document.id
        val modifiedDocument = document.copy(active = false)

        every { repository.findByMovieIdAndIdAndActive(movieId, id, true) } answers { just(document) }
        every { repository.save(modifiedDocument) } answers { just(modifiedDocument) }

        StepVerifier.create(service.delete(movieId, id))
            .verifyComplete()

        verify(exactly = 1) {
            repository.findByMovieIdAndIdAndActive(movieId, id, true)
            repository.save(modifiedDocument)
        }
    }
}