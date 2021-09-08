package com.coelhocaique.cinema.core.domain.review

import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.UUID

object ReviewMapper {

    fun toReviewResponse(
        document: ReviewDocument
    ) = just(
        ReviewResponse(
            id = document.id,
            movieId = document.referenceId,
            rating = document.rating,
            comment = document.comment,
            createdAt = document.createdAt
        )
    )

    fun toReviewDocument(
        referenceId: UUID,
        type: ReviewType,
        request: ReviewRequest
    ) = just(
        ReviewDocument(
            id = UUID.randomUUID(),
            referenceId = referenceId,
            type = type,
            comment = request.comment,
            rating = request.rating,
            createdAt = LocalDateTime.now()
        )
    )
}