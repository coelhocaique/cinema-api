package com.coelhocaique.cinema.core.service

import com.coelhocaique.cinema.core.mock.mockMovieDocument
import com.coelhocaique.cinema.core.mock.mockMovieRequest
import com.coelhocaique.cinema.core.mock.mockOmdbResponse
import com.coelhocaique.cinema.core.persistance.MovieDocument
import com.coelhocaique.cinema.core.persistance.MovieRepository
import com.coelhocaique.cinema.core.service.movie.MovieService
import com.coelhocaique.cinema.core.service.movie.client.OmdbClient
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.TestInstance
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieServiceTest {

    @Test
    fun testFindById() {
        val omdbClient = mockk<OmdbClient>()
        val movieRepository = mockk<MovieRepository>()
        val service = MovieService(movieRepository, omdbClient)
        val omdbResponse = mockOmdbResponse()
        val document = mockMovieDocument()
        val id = document.id

        every { movieRepository.findById(id) } answers { just(document) }
        every { omdbClient.retrieveMovieDetails(document.imdbId) } answers { just(omdbResponse) }

        StepVerifier.create(service.findById(id))
            .assertNext {
                assertEquals(document.id, it.id)
                assertEquals(omdbResponse.title, it.title)
                assertEquals(omdbResponse.imdbRating, it.imdbRating)
                assertEquals(omdbResponse.runtime, it.runtime)
                assertEquals(omdbResponse.released, it.released)
                assertEquals(omdbResponse.ratings, it.ratings)
            }
            .verifyComplete()


        verify(exactly = 1) { movieRepository.findById(id) }
        verify(exactly = 1) { omdbClient.retrieveMovieDetails(document.imdbId) }
    }

    @Test
    fun findAll() {
        val omdbClient = mockk<OmdbClient>()
        val movieRepository = mockk<MovieRepository>()
        val service = MovieService(movieRepository, omdbClient)
        val omdbResponse = mockOmdbResponse()
        val document = mockMovieDocument()

        every { movieRepository.findAll() } answers { Flux.just(document) }
        every { omdbClient.retrieveMovieDetails(document.imdbId) } answers { just(omdbResponse) }

        StepVerifier.create(service.findAll())
            .assertNext {
                assertEquals(1, it.size)
                assertEquals(document.id, it[0].id)
                assertEquals(omdbResponse.title, it[0].title)
                assertEquals(omdbResponse.imdbRating, it[0].imdbRating)
                assertEquals(omdbResponse.runtime, it[0].runtime)
                assertEquals(omdbResponse.released, it[0].released)
                assertEquals(omdbResponse.ratings, it[0].ratings)
            }
            .verifyComplete()

        verify(exactly = 1) { movieRepository.findAll() }
        verify(exactly = 1) { omdbClient.retrieveMovieDetails(document.imdbId) }
    }

    @Test
    fun create() {
        val omdbClient = mockk<OmdbClient>()
        val movieRepository = mockk<MovieRepository>()
        val service = MovieService(movieRepository, omdbClient)
        val omdbResponse = mockOmdbResponse()
        val document = mockMovieDocument()
        val movieRequest = mockMovieRequest()

        every { movieRepository.findByImdbId(movieRequest.imdbId!!) } answers { empty() }
        every { omdbClient.retrieveMovieDetails(document.imdbId) } answers { just(omdbResponse) }
        every { movieRepository.insert(any() as MovieDocument) } answers { just(document) }

        StepVerifier.create(service.create(movieRequest))
            .assertNext {
                assertEquals(document.id, it.id)
                assertEquals(omdbResponse.title, it.title)
                assertEquals(omdbResponse.imdbRating, it.imdbRating)
                assertEquals(omdbResponse.runtime, it.runtime)
                assertEquals(omdbResponse.released, it.released)
                assertEquals(omdbResponse.ratings, it.ratings)
            }
            .verifyComplete()


        verify(exactly = 1) { movieRepository.findByImdbId(movieRequest.imdbId!!) }
        verify(exactly = 1) { omdbClient.retrieveMovieDetails(document.imdbId) }
        verify(exactly = 1) { movieRepository.insert(any() as MovieDocument) }
    }
}