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
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just
import reactor.test.StepVerifier

class MovieServiceTest {

    @Test
    fun testFindById() {
        val omdbClient = mockk<OmdbClient>()
        val repository = mockk<MovieRepository>()
        val service = MovieService(repository, omdbClient)
        val omdbResponse = mockOmdbResponse()
        val document = mockMovieDocument()
        val id = document.id

        every { repository.findById(id) } answers { just(document) }
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

        verify(exactly = 1) {
            repository.findById(id)
            omdbClient.retrieveMovieDetails(document.imdbId)
        }
    }

    @Test
    fun findAll() {
        val omdbClient = mockk<OmdbClient>()
        val repository = mockk<MovieRepository>()
        val service = MovieService(repository, omdbClient)
        val omdbResponse = mockOmdbResponse()
        val document = mockMovieDocument()

        every { repository.findAll() } answers { Flux.just(document) }
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

        verify(exactly = 1) {
            repository.findAll()
            omdbClient.retrieveMovieDetails(document.imdbId)
        }
    }

    @Test
    fun create() {
        val omdbClient = mockk<OmdbClient>()
        val repository = mockk<MovieRepository>()
        val service = MovieService(repository, omdbClient)
        val omdbResponse = mockOmdbResponse()
        val movieRequest = mockMovieRequest()
        val imdbId = movieRequest.imdbId!!

        every { repository.findByImdbId(imdbId) } answers { empty() }
        every { omdbClient.retrieveMovieDetails(imdbId) } answers { just(omdbResponse) }
        every { repository.insert(any() as MovieDocument) } answers { just(it.invocation.args[0] as MovieDocument) }

        StepVerifier.create(service.create(movieRequest))
            .assertNext {
                assertNotNull(it.id)
                assertEquals(omdbResponse.title, it.title)
                assertEquals(omdbResponse.imdbRating, it.imdbRating)
                assertEquals(omdbResponse.runtime, it.runtime)
                assertEquals(omdbResponse.released, it.released)
                assertEquals(omdbResponse.ratings, it.ratings)
            }
            .verifyComplete()

        verify(exactly = 1) {
            repository.findByImdbId(imdbId)
            omdbClient.retrieveMovieDetails(imdbId)
            repository.insert(any() as MovieDocument)
        }
    }
}