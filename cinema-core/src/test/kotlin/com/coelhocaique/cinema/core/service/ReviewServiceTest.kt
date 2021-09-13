package com.coelhocaique.cinema.core.service

import com.coelhocaique.cinema.core.mock.mockMovieResponse
import com.coelhocaique.cinema.core.mock.mockMovieReviewDocument
import com.coelhocaique.cinema.core.mock.mockReviewRequest
import com.coelhocaique.cinema.core.persistance.ReviewDocument
import com.coelhocaique.cinema.core.persistance.ReviewRepository
import com.coelhocaique.cinema.core.service.movie.MovieService
import com.coelhocaique.cinema.core.service.review.ReviewService
import com.coelhocaique.cinema.core.service.review.ReviewType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class ReviewServiceTest {

    @Test
    fun findMovieReview() {
        val movieService = mockk<MovieService>()
        val repository = mockk<ReviewRepository>()
        val service = ReviewService(repository, movieService)
        val document = mockMovieReviewDocument()
        val movieId = document.referenceId

        every { repository.findByReferenceIdAndType(movieId, ReviewType.MOVIE) } answers { Flux.just(document) }

        StepVerifier.create(service.findMovieReview(movieId))
            .assertNext {
                assertEquals(1, it.size)
                assertEquals(document.id, it[0].id)
                assertEquals(document.comment, it[0].comment)
                assertEquals(document.rating, it[0].rating)
                assertEquals(document.referenceId, it[0].movieId)
                assertEquals(document.createdAt, it[0].createdAt)
            }
            .verifyComplete()


        verify(exactly = 1) { repository.findByReferenceIdAndType(movieId, ReviewType.MOVIE) }
    }

    @Test
    fun createMovieReview() {
        val movieService = mockk<MovieService>()
        val repository = mockk<ReviewRepository>()
        val service = ReviewService(repository, movieService)
        val movieResponse = mockMovieResponse()
        val movieId = movieResponse.id
        val request = mockReviewRequest()

        every { movieService.findById(movieId) } answers { Mono.just(movieResponse) }
        every { repository.insert(any() as ReviewDocument) } answers { Mono.just(it.invocation.args[0] as ReviewDocument) }

        StepVerifier.create(service.createMovieReview(movieId, request))
            .assertNext {
                assertNotNull(it.id)
                assertEquals(request.comment, it.comment)
                assertEquals(request.rating, it.rating)
                assertEquals(movieId, it.movieId)
                assertNotNull(it.createdAt)
            }
            .verifyComplete()


        verify(exactly = 1) {
            movieService.findById(movieId)
            repository.insert(any() as ReviewDocument)
        }
    }
}