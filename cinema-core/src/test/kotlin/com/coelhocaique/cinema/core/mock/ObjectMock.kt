package com.coelhocaique.cinema.core.mock

import com.coelhocaique.cinema.core.persistance.MovieDocument
import com.coelhocaique.cinema.core.service.movie.MovieRequest
import com.coelhocaique.cinema.core.service.movie.MovieResponse
import com.coelhocaique.cinema.core.service.movie.client.OmdbResponse
import com.coelhocaique.cinema.core.service.review.ReviewRequest
import com.coelhocaique.cinema.core.service.review.ReviewResponse
import com.coelhocaique.cinema.core.service.session.MovieSessionRequest
import com.coelhocaique.cinema.core.service.session.MovieSessionResponse
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

fun mockMovieResponse() = MovieResponse(
    id = UUID.randomUUID(),
    title = "The Fast and the Furious",
    imdbRating = 6.8,
    released = "22 Jun 2001",
    runtime = "106 min",
    ratings = emptyList()
)

fun mockMovieDocument() = MovieDocument(
    id = UUID.randomUUID(),
    imdbId = "tt0232500",
    createdAt = LocalDateTime.now()
)

fun mockMovieRequest() = MovieRequest(
    imdbId = "tt0232500"
)

fun mockOmdbResponse() = OmdbResponse(
    imdbId = "tt0232500",
    title = "The Fast and the Furious",
    imdbRating = 6.8,
    released = "22 Jun 2001",
    runtime = "106 min",
    ratings = emptyList()
)

fun mockMovieReviewResponse() = ReviewResponse(
    id = UUID.randomUUID(),
    movieId = UUID.randomUUID(),
    rating = 4.9,
    comment = "Awesome Movie!",
    createdAt = LocalDateTime.now()
)

fun mockReviewRequest() = ReviewRequest(
    rating = 4.9,
    comment = "Awesome Movie!"
)

fun mockMovieSessionResponse() = MovieSessionResponse(
    id = UUID.randomUUID(),
    movieId = UUID.randomUUID(),
    price = BigDecimal.ONE,
    room = "A1",
    time = LocalTime.of(18, 1),
    date = LocalDate.of(2021, 9, 9)
)

fun mockMovieSessionRequest() = MovieSessionRequest(
    price = BigDecimal.ONE,
    sessionDateTime = LocalDateTime.of(2021, 9, 9, 18, 0),
    room = "A1"
)