package com.coelhocaique.cinema.api.handler

import com.coelhocaique.cinema.api.handler.RequestParameterHandler.extractBody
import com.coelhocaique.cinema.api.handler.RequestParameterHandler.retrieveId
import com.coelhocaique.cinema.api.helper.RequestValidator.validate
import com.coelhocaique.cinema.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.cinema.core.service.review.ReviewRequest
import com.coelhocaique.cinema.core.service.review.ReviewService
import com.coelhocaique.cinema.core.service.review.ReviewType
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ReviewHandler(private val reviewService: ReviewService) {

    fun findMovieReviews(req: ServerRequest): Mono<ServerResponse> {
        return retrieveId(req)
            .flatMap { reviewService.find(it, ReviewType.MOVIE) }
            .let { generateResponse(it) }
    }

    fun createMovieReview(req: ServerRequest): Mono<ServerResponse> {
        return extractBody<ReviewRequest>(req)
            .flatMap { validate(it).zipWith(retrieveId(req)) }
            .flatMap { reviewService.createMovieReview(it.t2, it.t1) }
            .let { generateResponse(it, successStatus = HttpStatus.CREATED.value()) }
    }
}