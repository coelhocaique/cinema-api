package com.coelhocaique.cinema.api.unit

import com.coelhocaique.cinema.api.handler.MovieHandler
import com.coelhocaique.cinema.api.helper.RequestParameterHandler
import com.coelhocaique.cinema.api.helper.LinkBuilder
import com.coelhocaique.cinema.api.mock.mockMovieResponse
import com.coelhocaique.cinema.core.service.movie.MovieService
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just
import reactor.test.StepVerifier
import java.util.UUID

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieHandlerTest {

    private val uuid = UUID.randomUUID()

    @BeforeAll
    fun mockStatic() {
        mockkObject(RequestParameterHandler)
        mockkObject(LinkBuilder)
    }

    @Test
    fun testFindById() {
        val service = mockk<MovieService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieHandler(service)

        val response = mockMovieResponse()

        every { RequestParameterHandler.retrieveId(serverRequest) } answers { just(uuid) }
        every { service.findById(uuid) } answers { just(response) }
        every { LinkBuilder.addMovieResponseLinks(serverRequest, response) } answers { just(response) }

        StepVerifier.create(handler.findById(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.OK, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.findById(uuid) }
        verify(exactly = 1) { RequestParameterHandler.retrieveId(eq(serverRequest)) }
        verify(exactly = 1) { LinkBuilder.addMovieResponseLinks(serverRequest, response) }
    }

    @Test
    fun testFindByIdWhenMovieNotFound() {
        val service = mockk<MovieService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieHandler(service)

        every { RequestParameterHandler.retrieveId(serverRequest) } answers { just(uuid) }
        every { service.findById(uuid) } answers { empty() }

        StepVerifier.create(handler.findById(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.BAD_REQUEST, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.findById(uuid) }
        verify(exactly = 1) { RequestParameterHandler.retrieveId(eq(serverRequest)) }
    }

    @Test
    fun testFindAll() {
        val service = mockk<MovieService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieHandler(service)

        val response = listOf(mockMovieResponse())

        every { service.findAll() } answers { just(response) }
        every { LinkBuilder.addMovieResponseLinks(serverRequest, response) } answers { just(response) }

        StepVerifier.create(handler.findAll(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.OK, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.findAll() }
        verify { RequestParameterHandler.retrieveId(eq(serverRequest)) wasNot Called }
        verify(exactly = 1) { LinkBuilder.addMovieResponseLinks(serverRequest, response) }
    }

    @Test
    fun testFindAllWhenNoData() {
        val service = mockk<MovieService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieHandler(service)

        every { service.findAll() } answers { empty() }

        StepVerifier.create(handler.findAll(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.OK, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.findAll() }
        verify { RequestParameterHandler.retrieveId(eq(serverRequest)) wasNot Called }
    }
}