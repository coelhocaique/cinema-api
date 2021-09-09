package com.coelhocaique.cinema.api.unit

import com.coelhocaique.cinema.api.handler.MovieSessionHandler
import com.coelhocaique.cinema.api.handler.RequestParameterHandler
import com.coelhocaique.cinema.api.helper.LinkBuilder
import com.coelhocaique.cinema.api.mock.mockMovieSessionRequest
import com.coelhocaique.cinema.api.mock.mockMovieSessionResponse
import com.coelhocaique.cinema.core.service.session.MovieSessionRequest
import com.coelhocaique.cinema.core.service.session.MovieSessionService
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
class MovieSessionHandlerTest {

    private val uuid = UUID.randomUUID()

    @BeforeAll
    fun mockStatic() {
        mockkObject(RequestParameterHandler)
        mockkObject(LinkBuilder)
    }

    @Test
    fun testFindById() {
        val service = mockk<MovieSessionService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieSessionHandler(service)
        val response = mockMovieSessionResponse()

        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { service.findByMovieId(uuid) } answers { just(listOf(response)) }

        StepVerifier.create(handler.findByMovieId(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.OK, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.findByMovieId(uuid) }
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
    }

    @Test
    fun testFindByIdWhenNoDataFound() {
        val service = mockk<MovieSessionService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieSessionHandler(service)

        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { service.findByMovieId(uuid) } answers { empty() }

        StepVerifier.create(handler.findByMovieId(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.OK, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.findByMovieId(uuid) }
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
    }

    @Test
    fun testCreate() {
        val service = mockk<MovieSessionService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieSessionHandler(service)
        val request = mockMovieSessionRequest()
        val response = mockMovieSessionResponse()

        every { serverRequest.bodyToMono(MovieSessionRequest::class.java) } answers { just(request) }
        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { service.create(uuid, request) } answers { just(response) }

        StepVerifier.create(handler.create(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.CREATED, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.create(uuid, request) }
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.bodyToMono(MovieSessionRequest::class.java) }
    }

    @Test
    fun testCreateWhenInvalidRequest() {
        val service = mockk<MovieSessionService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieSessionHandler(service)
        val request = MovieSessionRequest()
        val response = mockMovieSessionResponse()

        every { serverRequest.bodyToMono(MovieSessionRequest::class.java) } answers { just(request) }
        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { service.create(uuid, request) } answers { just(response) }

        StepVerifier.create(handler.create(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.BAD_REQUEST, it.statusCode())
            }
            .verifyComplete()

        verify { service.create(uuid, request) wasNot Called}
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.bodyToMono(MovieSessionRequest::class.java) }
    }

    @Test
    fun testDelete() {
        val service = mockk<MovieSessionService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieSessionHandler(service)
        val response = mockMovieSessionResponse()

        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { RequestParameterHandler.retrieveId(serverRequest) } answers { just(uuid) }
        every { service.delete(uuid, uuid) } answers { just(response) }

        StepVerifier.create(handler.delete(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.NO_CONTENT, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.delete(uuid, uuid) }
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveId(eq(serverRequest)) }
    }

    @Test
    fun testDeleteWhenMovieSessionNotFound() {
        val service = mockk<MovieSessionService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = MovieSessionHandler(service)

        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { RequestParameterHandler.retrieveId(serverRequest) } answers { just(uuid) }
        every { service.delete(uuid, uuid) } answers { empty() }

        StepVerifier.create(handler.delete(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.NOT_FOUND, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.delete(uuid, uuid) }
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveId(eq(serverRequest)) }
    }
}