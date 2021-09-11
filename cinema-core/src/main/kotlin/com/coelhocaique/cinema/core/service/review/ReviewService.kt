package com.coelhocaique.cinema.core.service.review

import com.coelhocaique.cinema.core.service.review.ReviewMapper.toReviewDocument
import com.coelhocaique.cinema.core.service.review.ReviewMapper.toReviewResponse
import com.coelhocaique.cinema.core.persistance.ReviewRepository
import com.coelhocaique.cinema.core.service.movie.MovieService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.UUID

@Service
class ReviewService(private val reviewRepository: ReviewRepository,
                    private val movieService: MovieService) {

    fun findMovieReview(movieId: UUID): Mono<List<ReviewResponse>> {
        return reviewRepository.findByReferenceIdAndType(movieId, ReviewType.MOVIE)
            .flatMap { toReviewResponse(it) }
            .collectList()
    }

    fun createMovieReview(movieId: UUID, request: ReviewRequest): Mono<ReviewResponse> {
        return toReviewDocument(movieId, ReviewType.MOVIE, request)
            .flatMap { movieService.findById(it.referenceId).zipWith(just(it))}
            .flatMap { reviewRepository.insert(it.t2) }
            .flatMap { toReviewResponse(it) }
    }

}