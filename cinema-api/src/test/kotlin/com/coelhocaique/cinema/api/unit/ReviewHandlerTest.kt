package com.coelhocaique.cinema.api.unit

import com.coelhocaique.cinema.api.handler.RequestParameterHandler
import com.coelhocaique.cinema.api.handler.ReviewHandler
import com.coelhocaique.cinema.api.helper.LinkBuilder
import com.coelhocaique.cinema.api.mock.mockMovieReviewResponse
import com.coelhocaique.cinema.api.mock.mockReviewRequest
import com.coelhocaique.cinema.core.service.review.ReviewRequest
import com.coelhocaique.cinema.core.service.review.ReviewService
import com.coelhocaique.cinema.core.service.review.ReviewType
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
class ReviewHandlerTest {

    private val uuid = UUID.randomUUID()

    @BeforeAll
    fun mockStatic() {
        mockkObject(RequestParameterHandler)
        mockkObject(LinkBuilder)
    }

    @Test
    fun testMovieReviews() {
        val service = mockk<ReviewService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ReviewHandler(service)

        val response = mockMovieReviewResponse()

        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { service.find(uuid, ReviewType.MOVIE) } answers { just(listOf(response)) }

        StepVerifier.create(handler.findMovieReviews(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.OK, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.find(uuid, ReviewType.MOVIE) }
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
    }

    @Test
    fun testMovieReviewsWhenMovieNotFound() {
        val service = mockk<ReviewService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ReviewHandler(service)

        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { service.find(uuid, ReviewType.MOVIE) } answers { empty() }

        StepVerifier.create(handler.findMovieReviews(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.OK, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.find(uuid, ReviewType.MOVIE) }
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
    }

    @Test
    fun testCreateMovieReview() {
        val service = mockk<ReviewService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ReviewHandler(service)
        val request = mockReviewRequest()
        val response = mockMovieReviewResponse()

        every { serverRequest.bodyToMono(ReviewRequest::class.java) } answers { just(request) }
        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { service.createMovieReview(uuid, request) } answers { just(response) }

        StepVerifier.create(handler.createMovieReview(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.CREATED, it.statusCode())
            }
            .verifyComplete()

        verify(exactly = 1) { service.createMovieReview(uuid, request) }
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.bodyToMono(ReviewRequest::class.java) }
    }

    @Test
    fun testCreateMovieReviewWhenInvalidRequest() {
        val service = mockk<ReviewService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ReviewHandler(service)
        val request = ReviewRequest()
        val response = mockMovieReviewResponse()

        every { serverRequest.bodyToMono(ReviewRequest::class.java) } answers { just(request) }
        every { RequestParameterHandler.retrieveMovieId(serverRequest) } answers { just(uuid) }
        every { service.createMovieReview(uuid, request) } answers { just(response) }

        StepVerifier.create(handler.createMovieReview(serverRequest))
            .assertNext {
                assertEquals(HttpStatus.BAD_REQUEST, it.statusCode())
            }
            .verifyComplete()

        verify { service.createMovieReview(uuid, request) wasNot Called }
        verify(exactly = 1) { RequestParameterHandler.retrieveMovieId(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.bodyToMono(ReviewRequest::class.java) }
    }
}