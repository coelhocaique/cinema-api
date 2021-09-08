package com.coelhocaique.cinema.api.helper

import com.coelhocaique.cinema.api.helper.Fields.RATING
import com.coelhocaique.cinema.api.helper.Messages.INVALID_RATING_RANGE
import com.coelhocaique.cinema.api.helper.Messages.NOT_NULL
import com.coelhocaique.cinema.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.cinema.core.domain.review.ReviewRequest
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

object RequestValidator {

    fun validate(request: ReviewRequest): Mono<ReviewRequest> {
        return try {
            nonNull(request.rating, RATING)
            ratingRange(request.rating)
            just(request)
        } catch (e: IllegalArgumentException){
            error(business(e.message!!))
        }
    }

    private fun ratingRange(rating: Double) {
        if (rating < 1 || rating > 5) {
            throw IllegalArgumentException(INVALID_RATING_RANGE)
        }
    }

    private fun nonNull(o: Any?, attr: String) = requireNotNull(o) { NOT_NULL.format(attr) }

}