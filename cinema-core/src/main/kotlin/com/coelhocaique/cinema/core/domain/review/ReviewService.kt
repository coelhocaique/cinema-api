package com.coelhocaique.cinema.core.domain.review

import com.coelhocaique.cinema.core.domain.review.ReviewMapper.toReviewDocument
import com.coelhocaique.cinema.core.domain.review.ReviewMapper.toReviewResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {

    fun find(referenceId: UUID, type: ReviewType): Mono<List<ReviewResponse>> {
        return reviewRepository.findByReferenceIdAndType(referenceId, type)
            .flatMap { toReviewResponse(it) }
            .collectList()
    }

    fun createMovieReview(movieId: UUID, request: ReviewRequest): Mono<ReviewResponse> {
        return toReviewDocument(movieId, ReviewType.MOVIE, request)
            .flatMap { reviewRepository.save(it) }
            .flatMap { toReviewResponse(it) }
    }

}