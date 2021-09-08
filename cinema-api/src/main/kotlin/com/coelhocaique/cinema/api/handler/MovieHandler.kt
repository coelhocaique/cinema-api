package com.coelhocaique.cinema.api.handler

import com.coelhocaique.cinema.api.handler.RequestParameterHandler.extractBody
import com.coelhocaique.cinema.api.handler.RequestParameterHandler.retrieveId
import com.coelhocaique.cinema.api.helper.RequestValidator.validate
import com.coelhocaique.cinema.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.cinema.core.domain.movie.MovieService
import com.coelhocaique.cinema.core.domain.review.ReviewRequest
import com.coelhocaique.cinema.core.domain.review.ReviewService
import com.coelhocaique.cinema.core.domain.review.ReviewType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class MovieHandler(private val movieService: MovieService,
                   private val reviewService: ReviewService) {

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        return retrieveId(req)
            .flatMap { movieService.findById(it) }
            .let { generateResponse(it) }
    }

    fun findAll(req: ServerRequest): Mono<ServerResponse> {
        return generateResponse(movieService.findAll())
    }

    fun findReviews(req: ServerRequest): Mono<ServerResponse> {
        return retrieveId(req)
            .flatMap { reviewService.find(it, ReviewType.MOVIE) }
            .let { generateResponse(it) }
    }

    fun createReview(req: ServerRequest): Mono<ServerResponse> {
        return extractBody<ReviewRequest>(req)
            .flatMap { validate(it).zipWith(retrieveId(req)) }
            .flatMap { reviewService.createMovieReview(it.t2, it.t1) }
            .let { generateResponse(it) }
    }
}